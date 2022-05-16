describe('Page load test', () => {
    it('Checks if basket page loads correctly', () => {
        cy.visit('http://localhost:3000/payment/')
        cy.get('[placeholder="Imię"]').should('exist')
        cy.get('[placeholder="Nazwisko"]').should('exist')
        cy.get('[placeholder="Email"]').should('exist')
        cy.get('[placeholder="Ulica"]').should('exist')
        cy.get('[placeholder="Numer budynku"]').should('exist')
        cy.get('[placeholder="Kod pocztowy"]').should('exist')
        cy.get('[placeholder="Miasto"]').should('exist')
        cy.get('[placeholder="Telefon"]').should('exist')
        cy.get('[type="submit"]').should('exist')
        cy.get('nav').should('exist')
    })

    it('Checks if initial order is empty', () => {
        cy.contains('Do zapłaty: 0')
    })
})

describe('Navigation test', () => {
    beforeEach(() => {
        cy.visit('http://localhost:3000/payment/')
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

describe('Form test', () => {
    beforeEach(() => {
        cy.visit('http://localhost:3000/payment/')
    })

    it('Submits empty form', () => {
        cy.get('[type="submit"]').click()
        cy.url().should('eq', 'http://localhost:3000/payment/')
        cy.get('[placeholder="Imię"]').then($el => $el[0].checkValidity()).should('be.false')
    })

    it('Submits correct form', () => {
        cy.get('[placeholder="Imię"]').type('Adam')
        cy.get('[placeholder="Nazwisko"]').type('Nowak')
        cy.get('[placeholder="Email"]').type('adam@nowak.com')
        cy.get('[placeholder="Ulica"]').type('Adamska')
        cy.get('[placeholder="Numer budynku"]').type('1')
        cy.get('[placeholder="Kod pocztowy"]').type('11-111')
        cy.get('[placeholder="Miasto"]').type('Nowakowice')
        cy.get('[placeholder="Telefon"]').type('123456789')
        cy.get('[type="submit"]').click()
        cy.on('window:alert', (resp) => {
            expect(resp).to.eq('Zamówienie przyjęte')
        })
        cy.url().should('eq', 'http://localhost:3000/payment/?')
    })

    it('Adds Laptop to basket and checks if amount to pay is correct', () => {
        cy.get('[href="/products/"]').click()
        cy.get(':nth-child(4) > pre > button').click()
        cy.get('[href="/basket/"]').click()
        cy.get('[href="/payment/"]').click()
        cy.contains('Do zapłaty: 3000')
    })

    it('Removes laptop from basket and checks if amount to pay ib back to 0', () => {
        cy.get('[href="/products/"]').click()
        cy.get(':nth-child(4) > pre > button').click()
        cy.get('[href="/basket/"]').click()
        cy.get('ul').children().first().get('button').click()
        cy.get('[href="/payment/"]').click()
        cy.contains('Do zapłaty: 0')
    })
})
