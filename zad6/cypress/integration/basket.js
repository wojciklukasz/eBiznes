describe('Page load test', () => {
    it('Checks if basket page loads correctly', () => {
        cy.visit('http://localhost:3000/basket/')
        cy.contains('Produkt')
        cy.contains('Cena')
        cy.contains('Do kasy')
        cy.get('nav').should('exist')
    })

    it('Checks if initial basket is empty', () => {
        cy.contains('Do zapłaty: 0')
    })
})

describe('Navigation test', () => {
    beforeEach(() => {
        cy.visit('http://localhost:3000/basket/')
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

    it('Clicks on Do kasy hyperlink', () => {
        cy.get('[href="/payment/"]').click()
        cy.url().should('eq', 'http://localhost:3000/payment/')
    })
})

describe('Basket functionality test', () => {
    it('Adds Chleb and checks if it has been added correctly', () => {
        cy.visit('http://localhost:3000/products/')
        cy.get(':nth-child(1) > pre > button').click()
        cy.get('[href="/basket/"]').click()
        cy.get('ul').children().should('have.length.gt', 0)
        cy.get('ul').children().first().should('contain', 'Chleb')
        cy.get('.products').should('contain', 'Do zapłaty: 5')
    })

    it('Removes Chleb from basket', () => {
        cy.get('ul').children().first().get('button').click()
        cy.get('.products').should('contain', 'Do zapłaty: 0')
    })
})
