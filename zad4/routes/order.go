package routes

import (
	"cw4go/database"
	"cw4go/models"
	"github.com/labstack/echo/v4"
	"net/http"
)

func GetOrders(c echo.Context) error {
	var orders []models.Order

	result := database.Database.Find(&orders)
	if result.Error != nil {
		return c.String(http.StatusNotFound, "Items not found")
	}

	return c.JSON(http.StatusOK, orders)
}

func GetOrder(c echo.Context) error {
	id := c.Param("id")
	var order models.Order

	result := database.Database.Find(&order, id)
	if result.Error != nil {
		return c.String(http.StatusNotFound, "Item not found")
	}

	return c.JSON(http.StatusOK, order)
}

func SaveOrder(c echo.Context) error {
	order := new(models.Order)

	err := c.Bind(order)
	if err != nil {
		return c.String(http.StatusBadRequest, "Invalid body " + err.Error())
	}

	result := database.Database.Create(order)
	if result.Error != nil {
		return c.String(http.StatusBadRequest, "Database error " + result.Error.Error())
	}

	return c.JSON(http.StatusOK, order)
}
