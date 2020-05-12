# Backend API

## Boquet (admin only)

### 1. Stránkovací seznam

Stránkovací seznam všech kytic

`GET /api/bouquets[?page=0][&size=10]`

#### Request body
none

#### Request params
```
page : int  (optional def. 0)  stránka
size : int  (optional def. 10) počet záznamů na stránku
```

#### Response body
`class Slice<BouquetList>`

```javascript
{
    "content":[
        {"price":"200.00","image":null,"name":"Kytice hezká","id":13,"size":"MEDIUM","active":true},
        {"price":"10000000.00","image":null,"name":"Kyticke hezčí","id":14,"size":"LARGE","active":true},
        {"price":"100.10","image":null,"name":"Nejhezčí kytice","id":12,"size":"SMALL","active":true},
        {"price":"10000000.00","image":null,"name":"the EGG","id":15,"size":"LARGE","active":false}
    ],
    "pageable":{
        "sort":{"unsorted":false,"sorted":true,"empty":false},
        "pageNumber":0,
        "pageSize":10,
        "offset":0,
        "unpaged":false,
        "paged":true
    },
    "last":true,
    "first":true,
    "sort":{"unsorted":false,"sorted":true,"empty":false},
    "size":10,
    "number":0,
    "numberOfElements":4,
    "empty":false
}
```

### 2. Seznam kytic pro html select

`GET /api/boquets/list`

#### Request body
none

#### Request params
none

#### Response body
`class List<OptionList>`

```javascript
[
    {"name":"Kytice hezká","id":"13"},
    {"name":"Kyticke hezčí","id":"14"},
    {"name":"Nejhezčí kytice","id":"12"},
    {"name":"the EGG","id":"15"}
]
```

### 3. Vytvoření kytice




## Category (admin only)



## Flower (admin only)



## Profile



## Shop

### 1. Seznam sekcí

Seznam všech kategorií seřazených podle priority

`GET /api/shop`

#### Request body
none

#### Request params
none

#### Response body

`class List<CategoryList>`

```javascript
[
    {"name":"Narozeniny","id":6,"active":true},
    {"name":"Nové","id":3,"active":true},
    {"name":"Pohřeb","id":7,"active":true},
    {"name":"Překvapení","id":8,"active":false},
    {"name":"Sezonní nabídka","id":5,"active":true},
    {"name":"V akci","id":4,"active":true}
]
```

### 2. Dostupné nastavení pro filtrování

Dostupné parametry pro filtry, lze filtrovat i pro konkrétní kategorii

`GET /api/shop/filter[/{id}]`

#### Request body
none

#### Request params
```
id : int  (optional)  id kategorie
```

#### Response body
`class FilterInfo`

```javascript
{
"categories":[
        {"name":"Narozeniny","id":6,"active":true},
        {"name":"Nové","id":3,"active":true},
        {"name":"Pohřeb","id":7,"active":true},
        {"name":"Překvapení","id":8,"active":false},
        {"name":"Sezonní nabídka","id":5,"active":true},
        {"name":"V akci","id":4,"active":true}
    ],
    ,"flowers":[
        {"name":"Fialka","id":"10"},
        {"name":"Růže","id":"9"},
        {"name":"Slunečnice","id":"11"}
    ],
    "colors":[3,4,6,8],
    "sizes":[false,true,true],
    "prices":{"min":200.00,"max":10000000.00}
}
```

### 3. Stránkovací výsledky filtrování

Stránkovací seznam výsledků - všechny parametry jsou nepovinné

`POST /api/shop/filter[/{id}][?page=0][&size=10]`

#### Request body
`class FilterRequest`

```javascript
{
    "flowers": [1,2,3],         // optional - id kytky
    "colors": [1,2,3],          // optional - id barvy
    "sizes":[false,true,true],  // optional
    "prices": {                 // optional
        "min": 0
        "max": 100
    }
}
```


#### Request params
```
id   : int  (optional)         id kategorie
page : int  (optional def. 0)  stránka
size : int  (optional def. 10) počet záznamů na stránku
```

#### Response body
`class Slice<BouquetList>`

```javascript
{
    "content":[
        {"name":"Nejhezčí kytice","image":null,"price":"100.10","size":"SMALL","active":true,"id":12}
    ],
    "pageable":{
        "sort":{"unsorted":false,"sorted":true,"empty":false},
        "pageNumber":0,
        "pageSize":10,
        "offset":0,
        "unpaged":false,
        "paged":true
    },
    "last":true,
    "first":true,
    "sort":{"unsorted":false,"sorted":true,"empty":false},
    "size":10,
    "number":0,
    "numberOfElements":1,
    "empty":false
}
```

### 4. Stránkovací seznam kytic v kategorii

Seznam všech kategorií seřazených podle priority

`GET /api/shop/category/{id}`

#### Request body
none

#### Request params
```
id   : int        id kategorie
```

#### Response body
`class Slice<BouquetList>`

```javascript
{
    "content":[
        {"price":"200.00","image":null,"name":"Kytice hezká","id":13,"size":"MEDIUM","active":true},
        {"price":"10000000.00","image":null,"name":"Kyticke hezčí","id":14,"size":"LARGE","active":true},
        {"price":"100.10","image":null,"name":"Nejhezčí kytice","id":12,"size":"SMALL","active":true},
        {"price":"10000000.00","image":null,"name":"the EGG","id":15,"size":"LARGE","active":false}
    ],
    "pageable":{
        "sort":{"unsorted":false,"sorted":true,"empty":false},
        "pageNumber":0,
        "pageSize":10,
        "offset":0,
        "unpaged":false,
        "paged":true
    },
    "last":true,
    "first":true,
    "sort":{"unsorted":false,"sorted":true,"empty":false},
    "size":10,
    "number":0,
    "numberOfElements":4,
    "empty":false
}
```
### 5. Detail kytice

Základní informace o kytici

`GET /api/shop/bouquet/{id}`

#### Request body
none

#### Request params
```
id   : int        id kytice
```

#### Response body
`class BouquetDetail`

```javascript
{
    "bouquet":{
        "id":12,
        "name":"Nejhezčí kytice",
        "perex":"<p>popis A s <strong>HTML</strong></p>",
        "description":"",
        "image":null,
        "price":"100.10",
        "size":"SMALL",
        "active":true
    },
    "rating":5.0,
    "storage":-1
}
```

### 6. Už přihlášený uživatel přidal recenzi (signed user only)

Vrací jen http status kód 

- pokud dosud nepřidal recenzi 204 NO_CONTENT
- jinak 406 NOT_ACCEPTABLE

`GET /api/shop/bouquet/{id}/is-review`

#### Request body
none

#### Request params
```
id   : int        id kytice
```

#### Response body
none

### 7. Stránkovací seznam komentářu

Stránkovací seznam komentářu

`GET /api/shop/bouquet/{id}/reviews[?page=0][&size=10]`

#### Request body
none

#### Request params
```
id   : int        id kytice
page : int  (optional def. 0)  stránka
size : int  (optional def. 10) počet záznamů na stránku
```

#### Response body
`class Slice<ReviewList>`

```javascript
{
    "content":[
        {"rating":5,"created":"2020-05-12T09:25:22.854+0000","userName":"Surname","message":"Recenze nejleší květiny"}
    ],
    "pageable":{
        "sort":{"unsorted":false,"sorted":true,"empty":false},
        "pageNumber":0,
        "pageSize":10,
        "offset":0,
        "unpaged":false,
        "paged":true
    },
    "last":true,
    "first":true,
    "sort":{"unsorted":false,"sorted":true,"empty":false},
    "size":10,
    "number":0,
    "numberOfElements":1,
    "empty":false
}
```

### 8. Přidání komentáře ke květině

Seznam všech kategorií seřazených podle priority - vrací http status 201 CRAETED

`POST /api/shop/bouquet/{id}/reviews`

#### Request body
`class ReviewSubmit`

```javascript
{
    "message": "komentář",
    "rating": 5 // int(0 - 5)
}
```

#### Request params
```
id   : int        id kytice
```

#### Response body
id uživatele

## User (admin only)

### 1. Seznam sekcí

Seznam všech kategorií seřazených podle priority

`GET /api/shop`

#### Request body
none

#### Request params
none

#### Response body
`class `

```javascript

```
