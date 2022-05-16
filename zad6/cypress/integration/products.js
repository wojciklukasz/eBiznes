describe('Page load test', () => {
    it('Checks if products page loads correctly', () => {
        cy.visit('http://localhost:3000/products/')
        cy.contains('Produkt')
        cy.contains('Cena')
        cy.get('nav').should('exist')
    })
})

describe('Navigation test', () => {
    beforeEach(() => {
        cy.visit('http://localhost:3000/products/')
    })

    it('Clicks on Strona Główna hyperlink', () => {
        cy.get('[href="/"]').click()
        cy.url().should('eq', 'http://localhost:3000/')
    })

    it('Clicks on Produkty hyperlink', () => {
        cy.get('[href="/products/"]').click()
        cy.url().should('eq', 'http://localhost:3000/products/')
    })

    it('Clicks on Koszyk hyperlink', () => {
        cy.get('[href="/basket/"]').click()
        cy.url().should('eq', 'http://localhost:3000/basket/')
    })
})

describe('Products list test', () => {
    beforeEach(() => {
        cy.visit('http://localhost:3000/products/')
    })

    it('Checks if products list is not empty', () => {
        cy.get('ul').should('have.length.gt', 0)
    })

    it('Checks if correct sample products are loaded', () => {
        cy.get(':nth-child(1) > pre').should('contain', 'Chleb')
        cy.get(':nth-child(1) > pre').should('contain', '5')
        cy.get(':nth-child(1) > pre > button').should('exist')
        cy.get('ul').last().should('contain', 'Laptop')
        cy.get('ul').last().should('contain', '3000')
        cy.get('ul').last().get('button').last().should('exist')
    })
})
