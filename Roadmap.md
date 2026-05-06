# 🚀 Flowstate Analytics Roadmap (100 Dagen Plan)

## Fase 1: De Core Engine (Dag 0 - 20)
*Focus: Van een lege map naar een werkende data-pijplijn.*

### De Setup & Fundering
- [x] **Dag 0:** Project Genesis. Spring Boot & Vue setup. Repo online.
- [x] **Dag 1:** De Database Koppeling. PostgreSQL verbinden met Spring Boot.
- [x] **Dag 2:** Entity Mapping. De AnalyticEvent Java class mappen naar een SQL-tabel met JPA.
- [x] **Dag 3:** Data Ingestion API. Een `/track` endpoint maken dat JSON accepteert en opslaat.
- [x] **Dag 4:** CORS Mastery. Backend configureren zodat de Vue-app (poort 5173) veilig data mag sturen.
- [x] **Dag 5:** Postman Testing. Alle mogelijke foutmeldingen testen (lege data, foute JSON).

### De Tracker Script (JS/TS)
- [ ] **Dag 6:** Het "Pixel" Concept. Een simpel script maken dat op elke site kan draaien.
- [ ] **Dag 7:** Auto-capture. Script uitbreiden om automatisch `window.location` te pakken.
- [ ] **Dag 8:** Referrer Tracking. Vastleggen van welke site de bezoeker komt (`document.referrer`).
- [ ] **Dag 9:** Browser Fingerprinting. Anonieme ID's genereren zonder cookies (privacy-first).
- [ ] **Dag 10:** Minificatie. Je script verkleinen (minify) zodat het supersnel laadt.

### De Eerste Visualisaties
- [ ] **Dag 11:** Vue-API Connectie. Axios instellen in Vue om data uit Java te trekken.
- [ ] **Dag 12:** Dashboard Layout. De basis Grid-structuur met Tailwind CSS.
- [ ] **Dag 13:** The Counter Card. Een component dat het totaal aantal hits laat zien.
- [ ] **Dag 14:** Date Range Logic. Backend queries schrijven voor "Vandaag" vs "Gisteren".
- [ ] **Dag 15:** Line Chart Intro. Je eerste grafiek (bijv. met Chart.js of Recharts).

### Verfijning & Security
- [ ] **Dag 16:** Site ID Validatie. Alleen data accepteren van geregistreerde domeinen.
- [ ] **Dag 17:** User Agent Parsing. Java library toevoegen om "Windows", "iPhone", etc. te herkennen.
- [ ] **Dag 18:** Data Aggregatie. Hoe tel je 1 miljoen hits zonder dat je DB traag wordt? (Intro SQL aggregates).
- [ ] **Dag 19:** Error Handling UI. Mooie meldingen in Vue als de backend offline is.
- [ ] **Dag 20:** 🏁 **Eerste Milestone.** Een werkend prototype: Script op site A -> Data in Java -> Grafiek in Vue.

---

## Fase 2: De Professionalisering (Dag 21 - 50)
*Focus: Authenticatie, meerdere websites beheren en performance.*

- [ ] **Dag 21-30:** **Spring Security & JWT.** Zorg dat je kunt inloggen op je dashboard.
- [ ] **Dag 31-40:** **Multi-tenancy.** Gebruikers kunnen meerdere websites toevoegen aan hun account.
- [ ] **Dag 41-50:** **Geavanceerde Analytics.** "Time on Page" meten en "Bounce Rate" berekenen.

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