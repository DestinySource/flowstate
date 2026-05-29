# 🚀 Flowstate Analytics Roadmap (100 Dagen Plan)

## Fase 1: De Core Engine (Dag 0 - 21)
*Focus: Van een lege map naar een werkende data-pijplijn.*

### De Setup & Fundering
- [x] **Dag 0:** Project Genesis. Spring Boot & Vue setup. Repo online.
- [x] **Dag 1:** De Database Koppeling. PostgreSQL verbinden met Spring Boot.
- [x] **Dag 2:** Entity Mapping. De AnalyticEvent Java class mappen naar een SQL-tabel met JPA.
- [x] **Dag 3:** Data Ingestion API. Een `/track` endpoint maken dat JSON accepteert en opslaat.
- [x] **Dag 4:** CORS Mastery. Backend configureren zodat de Vue-app (poort 5173) veilig data mag sturen.
- [x] **Dag 5:** Postman Testing. Alle mogelijke foutmeldingen testen (lege data, foute JSON).

### De Tracker Script (JS/TS)
- [x] **Dag 6:** Het "Pixel" Concept. Een simpel script maken dat op elke site kan draaien.
- [x] **Dag 7:** Auto-capture. Script uitbreiden om automatisch `window.location` te pakken.
- [x] **Dag 8:** Referrer Tracking. Vastleggen van welke site de bezoeker komt (`document.referrer`).
- [x] **Dag 9:** Browser Fingerprinting. Anonieme ID's genereren zonder cookies (privacy-first).
- [ ] **Dag 10:** Minificatie. Je script verkleinen (minify) zodat het supersnel laadt.

### De Eerste Visualisaties
- [x] **Dag 11:** Vue-API Connectie. fetch in Vue om data uit Java te trekken.
- [x] **Dag 12:** Dashboard Layout. De basis Grid-structuur met Tailwind CSS.
- [x] **Dag 13:** The Counter Card. Een component dat het totaal aantal hits laat zien.
- [x] **Dag 14:** Date Range Logic. Backend queries schrijven voor "Vandaag" vs "Gisteren".
- [ ] **Dag 15:** Line Chart Intro. Je eerste grafiek (bijv. met Chart.js of Recharts).

### Verfijning & Security
- [x] **Dag 16:** Site ID Validatie. Alleen data accepteren van geregistreerde domeinen.
- [x] **Dag 17:** User Agent Parsing. Java library toevoegen om "Windows", "iPhone", etc. te herkennen.
- [x] **Dag 18:** Data Aggregatie. Hoe tel je 1 miljoen hits zonder dat je DB traag wordt? (Intro SQL aggregates).
- [x] **Dag 19:** Error Handling in the backend.
- [x] **Dag 20:** Error Handling UI improvements.
- [x] **Dag 21:** 🏁 **Eerste Milestone.** Een werkend prototype: Script op site A -> Data in Java -> Grafiek in Vue.

---

## Fase 2: De Professionalisering (Dag 22 - 50)
*Focus: Authenticatie, meerdere websites beheren en performance.*

### Spring Security & JWT (Dag 22 - 30)
*Doel: Het dashboard beveiligen zodat alleen geautoriseerde gebruikers bij de data kunnen.*

- [x] **Dag 22:** **User Entity & DB.** Een `User` class aanmaken in Java met rollen (bijv. `ROLE_USER`) en de bijbehorende PostgreSQL-tabel opzetten.
- [x] **Dag 23:** **Password Hashing.** BCrypt configureren in Spring Boot zodat wachtwoorden veilig (gehashed) in de database worden opgeslagen.
- [x] **Dag 24:** **Spring Security Fundering.** De security filter chain opzetten. Alle endpoints blokkeren behalve de publieke `/api/track` (deze moet openblijven voor je tracker script!).
- [ ] **Dag 25:** **JWT Utilities.** Een Java utility class schrijven die JSON Web Tokens kan genereren, signeren en valideren.
- [ ] **Dag 26:** **Login Endpoint.** Een `/api/auth/login` endpoint bouwen dat een geldige JWT token teruggeeft bij de juiste inloggegevens.
- [ ] **Dag 27:** **JWT Authentication Filter.** Een filter bouwen dat bij elk dashboard-verzoek checkt of de meegeleverde JWT in de HTTP Authorization Header geldig is.
- [ ] **Dag 28:** **Vue Auth Store.** Pinia store (of een composable) opzetten in Vue om de JWT-token en de ingelogde status van de gebruiker te beheren.
- [ ] **Dag 29:** **Vue Login Interface.** Een strakke login-pagina bouwen in Vue met Tailwind CSS en deze succesvol koppelen aan het backend endpoint.
- [ ] **Dag 30:** **Navigation Guards.** Vue Router zo instellen dat niet-ingelogde gebruikers automatisch naar de login-pagina worden omgeleid (Auth Middleware).

### Multi-Tenancy & Website Beheer (Dag 31 - 40)
*Doel: Zorgen dat één gebruiker meerdere websites kan tracken en de data strikt gescheiden blijft.*

- [ ] **Dag 31:** **Website Entity.** Een `Website` class maken in Java (`id`, `name`, `domain`, `siteId`) met een `@ManyToOne` relatie naar de `User`.
- [ ] **Dag 32:** **Website CRUD Backend.** Endpoints bouwen om een nieuwe website toe te voegen, op te halen of te verwijderen voor de ingelogde gebruiker.
- [ ] **Dag 33:** **Data Isolatie Backend.** Je `/api/track` en analytics-endpoints aanpassen zodat ze filteren op de specifieke `site_id` én valideren of die site daadwerkelijk toebehoort aan de opgevraagde context.
- [ ] **Dag 34:** **Vue Website Beheer UI.** Een pagina maken in je dashboard waar de gebruiker een overzicht ziet van zijn geregistreerde websites met een "Nieuwe site toevoegen" formulier.
- [ ] **Dag 35:** **Site Switcher Component.** Een dropdown component maken in de bovenbalk van je Vue dashboard waarmee de gebruiker direct kan wisselen tussen de statistieken van verschillende websites.
- [ ] **Dag 36:** **Dynamic Script Generator UI.** Een component maken dat de exacte tracking-code (inclusief de dynamische en unieke `data-site-id`) toont, zodat de gebruiker deze direct kan kopiëren.
- [ ] **Dag 37:** **Empty State & Onboarding.** Een 'Get Started' view tonen in Vue wanneer een ingelogde gebruiker nog geen websites heeft geregistreerd.
- [ ] **Dag 38:** **Database Unique Constraints.** Unieke indexen toevoegen in SQL zodat een specifiek domein niet dubbel geclaimd kan worden onder dezelfde gebruiker.
- [ ] **Dag 39:** **Postman Edge-Case Testing.** Testen en valideren wat er gebeurt als Gebruiker A via de API data probeert op te vragen van een website van Gebruiker B (moet resulteren in een harde `403 Forbidden`).
- [ ] **Dag 40:** **Refactor & Code Clean-up.** Backend en frontend opschonen, API-foutmeldingen in de UI netjes opvangen en ongebruikte imports verwijderen.

### Geavanceerde Analytics (Dag 41 - 50)
*Doel: Complexe engagement metrics berekenen zoals verblijfsduur en weigpercentage.*

- [ ] **Dag 41:** **Tracker Upgrade: Heartbeat.** Het JavaScript-tracker script uitbreiden om elke 15 seconden een lichte 'ping' (heartbeat) naar de backend te sturen zolang de bezoeker de pagina actief open heeft.
- [ ] **Dag 42:** **Session Tracking & Java Model.** Een sessie-id introduceeren in het script en een backend model/tabel opzetten om de starttijd en de meest recente heartbeat per sessie bij te houden.
- [ ] **Dag 43:** **Time on Page SQL Query.** Een efficiënte SQL-query schrijven die het verschil tussen de starttijd en de laatste heartbeat berekent om de gemiddelde verblijfsduur (Average Time on Page) te bepalen.
- [ ] **Dag 44:** **Bounce Rate Logica.** Een SQL-aggregatie schrijven om de Bounce Rate (weigpercentage) te berekenen: het percentage bezoekers dat slechts 1 pagina bekijkt en binnen een ingestelde tijd weer vertrekt.
- [ ] **Dag 45:** **Top Pages Query.** Een query schrijven die de meest bezochte URL's groepeert, telt en sorteert op basis van het aantal hits voor een gekozen tijdsperiode.
- [ ] **Dag 46:** **Vue Metrics Cards.** Drie nieuwe scorekaarten toevoegen bovenaan het Vue dashboard: "Gemiddelde Sessieduur", "Bounce Rate" en "Unieke Bezoekers".
- [ ] **Dag 47:** **Vue Top Pages Tabel.** Een overzichtelijke tabel bouwen in de UI die de best presterende pagina's toont, inclusief een subtiele progress-bar per rij voor visuele verhoudingen.
- [ ] **Dag 48:** **Apparaat & OS Distributie.** De gegevens uit de User Agent parser (van Dag 17) omzetten in een SQL-query en visualiseren in een Donut of Pie Chart (bijv. Mobiel vs. Desktop).
- [ ] **Dag 49:** **Database Indexering (Performance).** Database-indexen leggen op de meest opgevraagde kolommen (`site_id`, `created_at`, `url`) in de events-tabel om te zorgen dat de nieuwe queries ook met duizenden rijen razendsnel blijven.
- [ ] **Dag 50:** 🏁 **Tweede Milestone.** Een volledig beveiligd multi-tenant dashboard. Je logt in, switcht naadloos tussen je websites en ziet direct live berekende Bounce Rates, Top Pages en Time on Page statistieken in je UI.
---

## Fase 3: Enterprise Features (Dag 51 - 80)
*Focus: Real-time data en geavanceerde SQL.*

- [ ] **Dag 51-60:** **WebSockets.** Zie bezoekers live over je scherm bewegen.
- [ ] **Dag 61-70:** **Custom Goals.** Gebruikers kunnen instellen wanneer een klik een "conversie" is.
- [ ] **Dag 71-80:** **Reporting.** Automatische PDF of e-mail wekelijkse rapportages genereren in Java.

---

## Fase 4: De Finale & Portfolio (Dag 81 - 100)
*Focus: Deployen, testen en jezelf verkopen.*

- [ ] **Dag 81-90:** **Performance Tuning.** Database indexing en caching met Redis.
- [ ] **Dag 91-95:** **Deployment.** Dockerize je app en zet hem op een VPS of Cloud.
- [ ] **Dag 96-100:** **De Showreel.** Een demo-video maken, je README perfectioneren en sollicitaties versturen.