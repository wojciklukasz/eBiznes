package routes

import (
	"cw4go/database"
	"cw4go/models"
	"github.com/labstack/echo/v4"
	"net/http"
)

func GetProducts(c echo.Context) error {
	var products []models.Product

	result := database.Database.Find(&products)
	if result.Error != nil {
		return c.String(http.StatusNotFound, "Items not found")
	}

	return c.JSON(http.StatusOK, products)
}

func GetProduct(c echo.Context) error {
	id := c.Param("id")
	var product models.Product

	result := database.Database.Find(&product, id)
	if result.Error != nil {
		return c.String(http.StatusNotFound, "Item not found")
	}

	return c.JSON(http.StatusOK, product)
}

func SaveProduct(c echo.Context) error {
	product := new(models.Product)

	err := c.Bind(product)
	if err != nil {
		return c.String(http.StatusBadRequest, "Invalid body " + err.Error())
	}

	result := database.Database.Create(product)
	if result.Error != nil {
		return c.String(http.StatusBadRequest, "Database error " + result.Error.Error())
	}

	return c.JSON(http.StatusOK, product)
}
