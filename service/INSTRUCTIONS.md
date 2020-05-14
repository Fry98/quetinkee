# **quetnikee** - Search and Caching service
## API Reference
### 1) Search
Pro přidání kytice do vyhledávání je potřeba poslat JSON objekt do RabbitMQ fronty **items**. Objekt musí být ve tvaru:
```js
{
  "id": 5,         // ID kytice v databázi
  "name": "Růže"   // Název kytice k zaindexování
}
```
Pro úpravu názvu stačí odeslat stejný objekt s novým názvem kytice. Záznam na daném ID se přepíše.

Pro vymazání záznamu z vyhledávání je potřeba přidat do fronty objekt v tomto tvaru:
```js
{
  "id": 5,         // ID kytice k odstranění
  "remove": true
}
```

&nbsp;

Pro zobrazení výsledků vyhledávání lze použít API endpoint `http://localhost:4200/api/search`, kde se k zadání vyhledávaného termínu používá metoda **GET** s query string proměnnou **q**. Příklad:
```
GET http://localhost:4200/api/search?q=ruze
```
Tato adresa v případě úspěchu vrací pole ID vyhledaných kytic (viz `[5, 17, 42]`). V případě neplatného požadavku pak vrací status kód **400**.

&nbsp;

### 2) Caching
Pro přidání k objektu do cache je potřeba poslat JSON objekt do RabbitMQ fronty **cache**. Objekt musí být ve tvaru:
```js
{
  "id": 5,         // ID pomocí kterého lze objekt získat z cache
  "ttl": 10,       // Doba do smazání objektu z cache (v sekundách)
  "data": {}       // Objekt k uložení do cache
}
```
Doporučil bych použít ID kytice pro cachování požadavků na detail kytice. Dále bych cachoval požadavek na květiny z domovské stránky a požadavek na existující kategorie; obojí s nějakým arbitrárně vybraným ID *(např. -1)*.

&nbsp;

Pro získání objketu z cache je možné odeslat **GET** požadavek na endpoint `http://localhost:4200/api/cache`, kde query string proměnná **id** určuje ID vyhledáváného objektu. Příklad:
```
GET http://localhost:4200/api/cache?id=5
```
Tato adresa v případě úspěchu vrací uložený objekt jako JSON. V případě neplatného požadavku vrací status kód **400**. V případě toho, že žádný objekt se zadaným ID není uložený v cache vrací status kód **404**.
