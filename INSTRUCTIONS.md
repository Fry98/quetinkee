# INSTRUCTIONS
Nejdřív si musíte stáhout Node (https://nodejs.org); je asi jedno kterej build, já osobně běžim na Current. Kromě toho se taky ujistěte, že máte v PATHU zadanej Maven a jdou vám tak spouštět příkazy `mvn`. Nakonec ještě spusťte příkaz `npm i`, aby se vám nainstalovali všechny JavaScript dependence.

Pro zapnutí kompletního dev enviromentu zadejete do terminálu `npm run dev`. To spustí jak náš Spring Boot server, tak i vývojovej Webpack server pro práci na front-endu. Náš Java server beží na portu 80, Webpack portu na 8080. Vám by mělo úplně stačit mít v prohlížeci otřevřenej ten Webpack server. Je nastavenej tak, že proxyuje všechny requesty co jdou na `/api` na ten náš Java server. Důležitý je ještě poznamenat, že během vývoje ten náš server funguje vážně jenom jako API server a pokud se přes něj pokusíte dostat na nějakou normální stránku, tak vám vyhodí chybu, protože ten front-end neni zkompilovanej. Práce back-end týmu tak spočívá jenom v tom napsat API endpointy. Servování statickejch stránek už je vyřešený v build processu a všechno bude fungovat bez vašeho zásahu.

Nastevní projektu taky nabízí spuštění jenom jednotlivejch částí. Pokud třeba děláte jenom na designu front-endu a nepotřebujete běžet back-end, tak spusttě `npm run client` a jste good to go. Stejně tak, pokud píšete jenom API controller a vůbec vás front-end nezajímá tak spusťte `npm run server` *(nebo se můžete vykašlat na terminál a sputit to přímo přes vaše IDEčko)*.

Celej projekt se pak vybuildí do JARu přes `npm run build`, ale to asi nikdo potřebovat nebudete.

Ok, takže... doufám, že jsem to napsal aspoň trochu srozumitelně. Kdyžtak mi dejte vědět a já ty instrukce updatuju.
