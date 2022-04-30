package main

import (
	"cw4go/database"
	"cw4go/routes"
	"github.com/labstack/echo/v4"
	"net/http"
)

func main() {
	database.Connect()

	e := echo.New()
	e.GET("/", func(c echo.Context) error {
		return c.String(http.StatusOK, "Hello World")
	})

	e.GET("/category", routes.GetCategories)
	e.GET("/category/:id", routes.GetCategory)
	e.POST("/category/", routes.SaveCategory)

	e.GET("/customer", routes.GetCustomers)
	e.GET("/customer/:id", routes.GetCustomer)
	e.POST("/customer/", routes.SaveCustomer)

	e.GET("/manufacturer", routes.GetManufacturers)
	e.GET("/manufacturer/:id", routes.GetManufacturer)
	e.POST("/manufacturer/", routes.SaveManufacturer)

	e.GET("/order", routes.GetOrders)
	e.GET("/order/:id", routes.GetOrder)
	e.POST("/order/", routes.SaveOrder)

	e.GET("/product", routes.GetProducts)
	e.GET("/product/:id", routes.GetProduct)
	e.POST("/product/", routes.SaveProduct)

	err := e.Start(":8000")
	if err != nil {
		return
	}
}
