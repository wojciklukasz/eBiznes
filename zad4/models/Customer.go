package models

import "gorm.io/gorm"

type Customer struct {
	gorm.Model
	Username string
	Password string
}
