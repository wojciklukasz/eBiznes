describe('Page load test', () => {
    it('Checks if main page loads correctly', () => {
        cy.visit('http://localhost:3000/')
        cy.contains('Sklep na eBiznes')
        cy.get('nav').should('exist')
    })
})

describe('Navigation test', () => {
    beforeEach(() => {
        cy.visit('http://localhost:3000/')
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
