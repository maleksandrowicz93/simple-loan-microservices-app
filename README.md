# simple-loan-microservices-app

Aby uruchomić aplikację należy:

  - sklonować repozytorium do wybranego katalogu,
  - uruchmoić konsolę w głównym katalogu projektu,
  - wpisać docker-compose build,
  - uruchomić skrypt docker-build.sh.
  
Usługa CreateCredit może przyjmować jeden z następujących typów kredytu(nazwy produktów):

    CONSUMER("Kredyt konsumpcyjny"),
    MORTGAGE("Kredyt hipoteczny"),
    INVESTMENT("Kredyt inwestycyjny"),
    CONSOLIDATION("Kredyt konsolidayjny"),
    CARD("Karta kredytowa").

Do testowania plikacji i wywoływania usług zaleca się aplikację postman. Poniżej przykładowy JSON.

{
	"customerDto": {
		"firstName": "John1",
		"surname": "Brennox1",
		"pesel": "00000000001"
	},
	"productDto": {
		"productName": "CARD",
		"value": 500
	},
	"creditName": "john_brennox_0000000_CARD_500"
}

Zastosowano walidację o następujących warunkach brzegowych:

	- odrzuca się zapytanie z polami typu String o mniejszej ilości znaków, niż 2,
	- odrzuca się zapytanie z polami typu String, jeśli są parsowalne do liczby(poza nr pesel),
	- odrzuca się zapytanie z numerem pesel o długości innej niż 11 znaków,
	- odrzuca się zapytanie z numerem pesel zawierających litery,
	- odrzuca się zapytanie z kwotą mniejszą niż 100, 
	- odrzuca się zapytanie z nullami.

 
