package database

import (
	"cw5back/models"
	"gorm.io/driver/sqlite"
	"gorm.io/gorm"
)

var Database *gorm.DB = nil

func Connect() {
	db, err := gorm.Open(sqlite.Open("shop.db"))
	if err != nil {
		panic("DATABASE ERROR")
	}

	db.AutoMigrate(&models.Product{})
	
	Database = db
}
