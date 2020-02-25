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
		"firstName": "mati1",
		"surname": "aleks1",
		"pesel": "00000000001"
	},
	"productDto": {
		"productName": "CARD",
		"value": 500
	},
	"creditName": "mati_aleks_0000000_CARD_500"
}

 
