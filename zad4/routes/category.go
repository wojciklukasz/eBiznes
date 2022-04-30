package routes

import (
	"cw4go/database"
	"cw4go/models"
	"github.com/labstack/echo/v4"
	"net/http"
)

func GetCategories(c echo.Context) error {
	var categories []models.Category

	result := database.Database.Find(&categories)
	if result.Error != nil {
		return c.String(http.StatusNotFound, "Items not found")
	}

	return c.JSON(http.StatusOK, categories)
}

func GetCategory(c echo.Context) error {
	id := c.Param("id")
	var category models.Category

	result := database.Database.Find(&category, id)
	if result.Error != nil {
		return c.String(http.StatusNotFound, "Item not found")
	}

	return c.JSON(http.StatusOK, category)
}

func SaveCategory(c echo.Context) error {
	category := new(models.Category)

	err := c.Bind(category)
	if err != nil {
		return c.String(http.StatusBadRequest, "Invalid body " + err.Error())
	}

	result := database.Database.Create(category)
	if result.Error != nil {
		return c.String(http.StatusBadRequest, "Invalid " + result.Error.Error())
	}

	return c.JSON(http.StatusOK, category)
}
