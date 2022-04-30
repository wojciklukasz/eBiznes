package routes

import (
	"cw4go/database"
	"cw4go/models"
	"github.com/labstack/echo/v4"
	"net/http"
)

func GetManufacturers(c echo.Context) error {
	var manufacturers []models.Manufacturer

	result := database.Database.Find(&manufacturers)
	if result.Error != nil {
		return c.String(http.StatusNotFound, "Items not found")
	}

	return c.JSON(http.StatusOK, manufacturers)
}

func GetManufacturer(c echo.Context) error {
	id := c.Param("id")
	var manufacturer models.Manufacturer

	result := database.Database.Find(&manufacturer, id)
	if result.Error != nil {
		return c.String(http.StatusNotFound, "Item not found")
	}

	return c.JSON(http.StatusOK, manufacturer)
}

func SaveManufacturer(c echo.Context) error {
	manufacturer := new(models.Manufacturer)

	err := c.Bind(manufacturer)
	if err != nil {
		return c.String(http.StatusBadRequest, "Invalid body " + err.Error())
	}

	result := database.Database.Create(manufacturer)
	if result.Error != nil {
		return c.String(http.StatusBadRequest, "Database error " + result.Error.Error())
	}

	return c.JSON(http.StatusOK, manufacturer)
}
