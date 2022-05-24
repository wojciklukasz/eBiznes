package database

import (
	"cw6back/models"
	"gorm.io/driver/sqlite"
	"gorm.io/gorm"
)

var Database *gorm.DB = nil

func Connect() {
	db, err := gorm.Open(sqlite.Open("users.db"))
	if err != nil {
		panic("DATABASE ERROR")
	}

	db.AutoMigrate(&models.User{})

	Database = db
}
