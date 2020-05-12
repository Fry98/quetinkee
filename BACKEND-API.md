# Backend API

- nepovinné parametry jsou označeny (optional) jen u vytváření
- u User a Profile jsou nyní nezdokumentované enpointy pro editaci adresy
- 100% nefunguje mazání a profile - forget password
- PATCH podporuje update jen změněných parametrů, ale seznamy klíčů u kytice se berou jako celek!

## Bouquet (admin only)

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

`GET /api/bouquets/list`

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

### 3. Detail kytice

Základní data z kytice jsou v "bouquet", ostatní vlastnosti jsou v seznamech klíču.

`GET /api/bouquets/{id}`

#### Request body
none

#### Request params
```
id   : int        id kytice
```

#### Response body
`class BouquetEdit`

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
    "keyCategories":[   // id kategorie
        3,4
    ],
    "keyColors":[       // id barev
        1
    ],
    "keyFlowerCount":{  // id květiny : počet ks
        "9":1,
        "10":1
    },
    "id":12
}
```

### 4. Založení kytice
Data kytice jsou ve stejném formátu jako u GET, nutné odeslat jako Content-Type: multipart/form-data

- part "bouquet" obsahuje data o kytici
- part "blob" (optional) obrázek kytice

`POST /api/bouquets`

#### Curl
`curl -X POST -H "Content-Type: multipart/form-data" -F "blob=@/home/davee/img.jpg" -F "bouquet={\"bouquet\":{\"name\":\"Filekytka\",\"perex\":\"Perex is cool\",\"price\": 100}};type=application/json" http://localhost/api/bouquets`

#### Request body
`class BouquetEdit`

```javascript
{
    "bouquet":{
        "name":"Nejhezčí kytice",
        "perex":"<p>popis A s <strong>HTML</strong></p>",
        "description":"",   // optional
        "price":"100.10",
        "size":"SMALL",     // optional
        "active":true       // optional default - false
    },
    "keyCategories":[   // id kategorie
        3,4
    ],
    "keyColors":[       // id barev
        1
    ],
    "keyFlowerCount":{  // id květiny : počet ks
        "9":1,
        "10":1
    },
}
```

#### Request params
none

#### Response body
id kytice


### 5. Aktualizace kytice
Data kytice jsou ve stejném formátu jako u GET, nutné odeslat jako Content-Type: multipart/form-data

- part "bouquet" obsahuje data o kytici
- part "blob" (optional) obrázek kytice

`PATCH /api/bouquets/{id}`

#### Request body
`class BouquetEdit`

```javascript
{
    "bouquet":{
        "name":"Nejhezčí kytice",
        "perex":"<p>popis A s <strong>HTML</strong></p>",
        "description":"",
        "price":"100.10",
        "size":"SMALL",
        "active":true
    },
    "keyCategories":[   // id kategorie
        3,4
    ],
    "keyColors":[       // id barev
        1
    ],
    "keyFlowerCount":{  // id květiny : počet ks
        "9":1,
        "10":1
    },
}
```

#### Request params
```
id   : int        id kytice
```

#### Response body
`class BouquetEdit`

```javascript
{
    "bouquet":{
        "id": 12,
        "name":"Nejhezčí kytice",
        "perex":"<p>popis A s <strong>HTML</strong></p>",
        "description":"",
        "image":null,
        "price":"100.10",
        "size":"SMALL",
        "active":true
    },
    "keyCategories":[   // id kategorie
        3,4
    ],
    "keyColors":[       // id barev
        1
    ],
    "keyFlowerCount":{  // id květiny : počet ks
        "9":1,
        "10":1
    },
    "id": 12
}
```

### 6. Smazání kytice

`DELETE /api/bouquets/{id}`

#### Request body
none

#### Request params
```
id   : int        id kytice
```

#### Response body
none






## Category (admin only)

### 1. Stránkovací seznam

Stránkovací seznam všech kategorií

`GET /api/categories[?page=0][&size=10]`

#### Request body
none

#### Request params
```
page : int  (optional def. 0)  stránka
size : int  (optional def. 10) počet záznamů na stránku
```

#### Response body
`class Slice<CategoriesList>`

```javascript
{
    "content":[
        {"active":true,"name":"Nové","id":3},
        {"active":false,"name":"Překvapení","id":8},
        {"active":true,"name":"V akci","id":4},
        {"active":true,"name":"Sezonní nabídka","id":5},
        {"active":true,"name":"Narozeniny","id":6},
        {"active":true,"name":"Pohřeb","id":7}
    ],
    "pageable":...
}
```

### 2. Seznam kategorií pro html select

`GET /api/categories/list`

#### Request body
none

#### Request params
none

#### Response body
`class List<OptionList>`

```javascript
[
    {"name":"Nové","id":"3"},
    {"name":"Překvapení","id":"8"},
    {"name":"V akci","id":"4"},
    {"name":"Sezonní nabídka","id":"5"},
    {"name":"Narozeniny","id":"6"},
    {"name":"Pohřeb","id":"7"}
]
```

### 3. Detail kategorie

`GET /api/categories/{id}`

#### Request body
none

#### Request params
```
id   : int        id kategorie
```

#### Response body
`class Category`

```javascript
{
    "id":3,
    "name":"Nové",
    "priority":1,
    "active":true
}
```

### 4. Založení kategorie
Data kategorie jsou ve stejném formátu jako u GET

`POST /api/categories`

#### Request body
`class Category`

```javascript
{
    "name":"Nové",
    "priority":1,
    "active":true   // optional default - false
}
```

#### Request params
none

#### Response body
id kategorie

### 5. Aktualizace kategorie
Data kategorie jsou ve stejném formátu jako u GET

`PATCH /api/categories/{id}`

#### Request body
`class Category`

```javascript
{
    "name":"Nové",
    "priority":1,
    "active":true
}
```

#### Request params
```
id   : int        id kategorie
```

#### Response body
`class Category`

```javascript
{
    "id":3,
    "name":"Nové",
    "priority":1,
    "active":true
}
```

### 6. Smazání kategorie

`DELETE /api/categories/{id}`

#### Request body
none

#### Request params
```
id   : int        id kategorie
```

#### Response body
none






## Flower (admin only)
### 1. Stránkovací seznam

Stránkovací seznam všech květin

`GET /api/flowers[?page=0][&size=10]`

#### Request body
none

#### Request params
```
page : int  (optional def. 0)  stránka
size : int  (optional def. 10) počet záznamů na stránku
```

#### Response body
`class Slice<FlowerList>`

```javascript
{
    "content":[
        {"price":"20.00","name":"Fialka","id":10},
        {"price":"10.00","name":"Růže","id":9},
        {"price":"30.00","name":"Slunečnice","id":11}
    ],
    "pageable":...
}
```

### 2. Seznam květin pro html select

`GET /api/flowers/list`

#### Request body
none

#### Request params
none

#### Response body
`class List<OptionList>`

```javascript
[
    {"name":"Fialka","id":"10"},
    {"name":"Růže","id":"9"},
    {"name":"Slunečnice","id":"11"}
]
```

### 3. Detail květiny

`GET /api/flowers/{id}`

#### Request body
none

#### Request params
```
id   : int        id květiny
```

#### Response body
`class Flower`

```javascript
{
    "id":10,
    "name":"Fialka",
    "description":"Note",
    "price":"20.00"
}
```

### 4. Založení květiny
Data květiny jsou ve stejném formátu jako u GET

`POST /api/flowers`

#### Request body
`class Flower`

```javascript
{
    "name":"Fialka",
    "description":"Note",   // optional
    "price":"20.00"
}
```

#### Request params
none

#### Response body
id květiny

### 5. Aktualizace květiny
Data květiny jsou ve stejném formátu jako u GET

`PATCH /api/flowers/{id}`

#### Request body
`class Flower`

```javascript
{
    "name":"Fialka",
    "description":"Note",
    "price":"20.00"
}
```

#### Request params
```
id   : int        id květiny
```

#### Response body
`class Flower`

```javascript
{
    "id":10,
    "name":"Fialka",
    "description":"Note",
    "price":"20.00"
}
```

### 6. Smazání květiny

`DELETE /api/flowers/{id}`

#### Request body
none

#### Request params
```
id   : int        id květiny
```

#### Response body
none






## Profile

### 1. Registrace zákazníka

`POST /api/profile`

#### Request body
`class User`

```javascript
{
    "firstName":"Name",
    "lastName":"Surname",
    "mail":"admin@admin.cz",
    "phone":"123456",
    "password": "heslo",    // povinné při vytváření jinak optinal
    "addressDelivery":{
        "street":"Street",
        "city":"City",
        "zip":"12345"
    },
    "addressBilling":{      // optinal
        "street":"Street",
        "city":"City",
        "zip":"12345"
    }
}
```

#### Request params
none

#### Response body
id uživatele

### 2. Detail zákazníka (signed user only)

`GET /api/profile`

#### Request body
none

#### Request params
none

#### Response body
`class User`

```javascript
{
    "id":1,
    "firstName":"Name",
    "lastName":"Surname",
    "phone":"123456",
    "password": "heslo",
    "addressDelivery":{
        "id":2,
        "street":"Street",
        "city":"City",
        "zip":"12345"
    },
    "addressBilling":{
        "id":3,
        "street":"Street",
        "city":"City",
        "zip":"12345"
    }
}
```

### 3. Aktualizace zákazníka (signed user only)

`PATCH /api/profile`

#### Request body
`class User`

```javascript
{
    "firstName":"Name",
    "lastName":"Surname",
    "phone":"123456",
    "password": "heslo",
    "addressDelivery":{
        "street":"Street",
        "city":"City",
        "zip":"12345"
    },
    "addressBilling":{
        "street":"Street",
        "city":"City",
        "zip":"12345"
    }
}
```

#### Request params
none

#### Response body
`class User`

```javascript
{
    "id":1,
    "firstName":"Name",
    "lastName":"Surname",
    "phone":"123456",
    "password": "heslo",
    "addressDelivery":{
        "id":2,
        "street":"Street",
        "city":"City",
        "zip":"12345"
    },
    "addressBilling":{
        "id":3,
        "street":"Street",
        "city":"City",
        "zip":"12345"
    }
}
```

### 4. Zapomenuté heslo

`POST /api/profile/forgot`

#### Request body
TODO mail

#### Request params
none

#### Response body
TODO asi jen status OK / fail


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

### 8. Přidání komentáře ke květině (signed user only)

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

### 1. Stránkovací seznam

Stránkovací seznam všech uživatelů

`GET /api/users[?page=0][&size=10]`

#### Request body
none

#### Request params
```
page : int  (optional def. 0)  stránka
size : int  (optional def. 10) počet záznamů na stránku
```

#### Response body
`class Slice<UserList>`

```javascript
{
    "content":[
        {
            "mail":"admin@admin.cz",
            "firstName":"Name",
            "lastName":"Surname",
            "id":1
        }
    ],
    "pageable":...
}
```

### 2. Seznam uživatelů pro html select

`GET /api/users/list`

#### Request body
none

#### Request params
none

#### Response body
`class List<OptionList>`

```javascript
[
    {"name":"admin@admin.cz","id":"1"}
]
```

### 3. Detail uživatele

`GET /api/users/{id}`

#### Request body
none

#### Request params
```
id   : int        id uživatele
```

#### Response body
`class User`

```javascript
{
    "id":1,
    "firstName":"Name",
    "lastName":"Surname",
    "mail":"admin@admin.cz",
    "phone":"123456",
    "role":"ADMIN",
    "addressDelivery":{
        "id":2,
        "street":"Street",
        "city":"City",
        "zip":"12345"
    },
    "addressBilling":{
        "id":3,
        "street":"Street",
        "city":"City",
        "zip":"12345"
    }
}
```

### 4. Založení uživatele
Data uživatele jsou ve stejném formátu jako u GET

`POST /api/users`

#### Request body
`class User`

```javascript
{
    "firstName":"Name",
    "lastName":"Surname",
    "mail":"admin@admin.cz",
    "phone":"123456",
    "password": "heslo",    // povinné při vytváření jinak optinal
    "role":"ADMIN",
    "addressDelivery":{
        "street":"Street",
        "city":"City",
        "zip":"12345"
    },
    "addressBilling":{      // optinal
        "street":"Street",
        "city":"City",
        "zip":"12345"
    }
}
```

#### Request params
none

#### Response body
id uživatele

### 5. Aktualizace uživatele
Data uživatele jsou ve stejném formátu jako u GET

`PATCH /api/users/{id}`

#### Request body
`class User`

```javascript
{
    "firstName":"Name",
    "lastName":"Surname",
    "mail":"admin@admin.cz",
    "phone":"123456",
    "role":"ADMIN",
    "password": "heslo",
    "addressDelivery":{
        "street":"Street",
        "city":"City",
        "zip":"12345"
    },
    "addressBilling":{
        "street":"Street",
        "city":"City",
        "zip":"12345"
    }
}
```

#### Request params
```
id   : int        id uživatele
```

#### Response body
`class User`

```javascript
{
    "id":1,
    "firstName":"Name",
    "lastName":"Surname",
    "mail":"admin@admin.cz",
    "phone":"123456",
    "role":"ADMIN",
    "addressDelivery":{
        "id":2,
        "street":"Street",
        "city":"City",
        "zip":"12345"
    },
    "addressBilling":{
        "id":2,
        "street":"Street",
        "city":"City",
        "zip":"12345"
    }
}
```

### 6. Smazání uživatele

`DELETE /api/users/{id}`

#### Request body
none

#### Request params
```
id   : int        id uživatele
```

#### Response body
none
