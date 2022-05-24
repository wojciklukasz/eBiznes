package main

import (
	"context"
	"cw6back/controllers"
	"cw6back/database"
	"github.com/joho/godotenv"
	"github.com/labstack/echo/v4"
	"github.com/labstack/echo/v4/middleware"
	"golang.org/x/oauth2"
	"golang.org/x/oauth2/github"
	"golang.org/x/oauth2/google"
	"net/http"
	"os"
)

func main() {
	database.Connect()

	err := godotenv.Load(".env")
	if err != nil {
		print("Error loading .env file!\n")
	}


	configGoogle := &oauth2.Config{
		ClientID:     os.Getenv("GOOGLE_CLIENTID"),
		ClientSecret: os.Getenv("GOOGLE_CLIENTSECRET"),
		RedirectURL:  "http://localhost:8000/auth/google/callback",
		Scopes: []string{
			"https://www.googleapis.com/auth/userinfo.email",
			"https://www.googleapis.com/auth/userinfo.profile",
			"openid",
		},
		Endpoint: google.Endpoint,
	}

	configGitHub := &oauth2.Config{
		ClientID: os.Getenv("GITHUB_CLIENTID"),
		ClientSecret: os.Getenv("GITHUB_CLIENTSECRET"),
		Endpoint: github.Endpoint,
		Scopes: []string{"user:email"},
	}

	e := echo.New()
	e.Use(middleware.CORSWithConfig(middleware.CORSConfig{
		AllowOrigins: []string{"http://localhost:3000"},
		AllowHeaders: []string{echo.HeaderOrigin, echo.HeaderContentType, echo.HeaderAccept, echo.HeaderAccessControlAllowOrigin, echo.HeaderAccessControlAllowCredentials},
		AllowMethods: []string{http.MethodGet, http.MethodPost},
	}))

	e.GET("/", func(c echo.Context) error {
		return c.String(http.StatusOK, "The frontend is at localhost:3000")
	})

	e.GET("/google", func(c echo.Context) error {
		url := controllers.GetLoginURL(configGoogle)
		return c.JSON(http.StatusOK, map[string]string{"url": url})
	})

	e.GET("/github", func(c echo.Context) error {
		url := controllers.GetLoginURL(configGitHub)
		return c.JSON(http.StatusOK, map[string]string{"url": url})
	})

	e.GET("/auth/google/callback", func(c echo.Context) error {
		token := controllers.GetTokenFromWeb(configGoogle, c.QueryParam("code"))
		info, err := controllers.FetchGoogleUserInfo(configGoogle.Client(context.Background(), token))
		if err != nil {
			return c.JSON(http.StatusBadRequest, map[string]string{"error": "Failed to fetch user info"})
		}

		found := controllers.FindUser(info.Email, "google")
		if found == false {
			controllers.AddUser(info.Email, "google", *token)
		}
		u := controllers.GetUser(info.Email, "google")

		c.Redirect(http.StatusFound, "http://localhost:3000/login/google/" + u.GoToken + "&" + u.Email)

		return c.JSON(http.StatusOK, map[string]string{"token": token.AccessToken})
	})

	e.GET("/auth/github/callback", func(c echo.Context) error {
		token := controllers.GetTokenFromWeb(configGitHub, c.QueryParam("code"))
		email, err := controllers.FetchGithubUserEmail(configGitHub.Client(context.Background(), token))
		if err != nil {
			return c.JSON(http.StatusBadRequest, map[string]string{"error": "Failed to fetch user info"})
		}

		found := controllers.FindUser(email, "github")
		if found == false {
			controllers.AddUser(email, "github", *token)
		}
		u := controllers.GetUser(email, "github")

		c.Redirect(http.StatusFound, "http://localhost:3000/login/github/" + u.GoToken + "&" + u.Email)
		return c.JSON(http.StatusOK, map[string]string{"token": token.AccessToken})
	})

	e.Logger.Fatal(e.Start(":8000"))
}
