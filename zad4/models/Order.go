package models

import (
	"gorm.io/gorm"
	"time"
)

type Order struct {
	gorm.Model
	Date time.Time
	CustomerID int
	Customer Customer
	Total float32
}
