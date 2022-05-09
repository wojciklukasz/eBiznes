package routes

import (
	"github.com/labstack/echo/v4"
	"net/http"
)

func ProcessPayment(c echo.Context) error {
	return c.String(http.StatusOK, "OK")
}
