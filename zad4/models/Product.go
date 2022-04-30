package models

import "gorm.io/gorm"

type Product struct {
	gorm.Model
	Name string
	Price float32
	CategoryID int
	Category Category
	ManufacturerID int
	Manufacturer Manufacturer
}
