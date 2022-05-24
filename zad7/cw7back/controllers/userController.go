package controllers

import (
	"context"
	"cw6back/database"
	"cw6back/models"
	"encoding/json"
	"github.com/google/uuid"
	"golang.org/x/oauth2"
	"io/ioutil"
	"log"
	"net/http"
	"strings"
)

func FindUser(email string, service string) bool {
	var user models.User
	database.Database.Find(&user, "Email = ? AND Service = ?", email, service)
	if user.Email == "" {
		return false
	}
	return true
}

func GetUser(email string, service string) models.User {
	var user models.User
	database.Database.Find(&user, "Email = ? AND Service = ?", email, service)
	return user
}

func AddUser(email string, service string, token oauth2.Token) models.User {
	user := new(models.User)
	user.Email = email
	user.Service = service
	user.Token = token.AccessToken
	user.GoToken = uuid.NewString()
	database.Database.Create(user)
	return GetUser(email, service)
}

func GetLoginURL(config *oauth2.Config) string {
	url := config.AuthCodeURL("state")
	return url
}

func GetTokenFromWeb(config *oauth2.Config, code string) *oauth2.Token {
	tok, err := config.Exchange(context.Background(), code)

	if err != nil {
		log.Fatal(err)
	}

	return tok
}

type UserInfo struct {
	Sub           string `json:"sub"`
	Name          string `json:"name"`
	GivenName     string `json:"given_name"`
	FamilyName    string `json:"family_name"`
	Profile       string `json:"profile"`
	Picture       string `json:"picture"`
	Email         string `json:"email"`
	EmailVerified bool   `json:"email_verified"`
	Gender        string `json:"gender"`
}

func FetchGoogleUserInfo(client *http.Client) (*UserInfo, error) {
	resp, err := client.Get("https://www.googleapis.com/oauth2/v3/userinfo")
	if err != nil {
		return nil, err
	}
	defer resp.Body.Close()
	data, err := ioutil.ReadAll(resp.Body)
	if err != nil {
		return nil, err
	}
	var result UserInfo
	if err := json.Unmarshal(data, &result); err != nil {
		return nil, err
	}
	return &result, nil
}


func FetchGithubUserEmail(client *http.Client) (string, error) {
	resp, err := client.Get("https://api.github.com/user/emails")
	if err != nil {
		return "a", err
	}
	defer resp.Body.Close()
	data, err := ioutil.ReadAll(resp.Body)
	if err != nil {
		return "a", err
	}
	str := string(data)
	email := strings.Replace(strings.Split(strings.Split(strings.Split(str, "}")[0], ",")[0], ":")[1], "\"", "", -1)
	return email, err
}