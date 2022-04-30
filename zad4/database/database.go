package database

import (
	"cw4go/models"
	"gorm.io/driver/sqlite"
	"gorm.io/gorm"
)

var Database *gorm.DB = nil

func Connect() {
	db, err := gorm.Open(sqlite.Open("shop.db"))
	if err != nil {
		panic("DATABASE ERROR")
	}

	db.AutoMigrate(&models.Category{})
	db.AutoMigrate(&models.Customer{})
	db.AutoMigrate(&models.Manufacturer{})
	db.AutoMigrate(&models.Order{})
	db.AutoMigrate(&models.Product{})

	Database = db
}
