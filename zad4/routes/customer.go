package routes

import (
"cw4go/database"
"cw4go/models"
"github.com/labstack/echo/v4"
"net/http"
)

func GetCustomers(c echo.Context) error {
	var customers []models.Customer

	result := database.Database.Find(&customers)
	if result.Error != nil {
		return c.String(http.StatusNotFound, "Items not found")
	}

	return c.JSON(http.StatusOK, customers)
}

func GetCustomer(c echo.Context) error {
	id := c.Param("id")
	var customer models.Customer

	result := database.Database.Find(&customer, id)
	if result.Error != nil {
		return c.String(http.StatusNotFound, "Item not found")
	}

	return c.JSON(http.StatusOK, customer)
}

func SaveCustomer(c echo.Context) error {
	customer := new(models.Customer)

	err := c.Bind(customer)
	if err != nil {
		return c.String(http.StatusBadRequest, "Invalid body " + err.Error())
	}

	result := database.Database.Create(customer)
	if result.Error != nil {
		return c.String(http.StatusBadRequest, "Database error " + result.Error.Error())
	}

	return c.JSON(http.StatusOK, customer)
}
