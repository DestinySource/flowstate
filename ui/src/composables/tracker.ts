interface AnalyticsPayload {
    visitorId: string;
    url: string;
    referrer: string;
    siteId: string;
    eventName: string;
    description: string;
}

(function () {
    const scriptTag = document.querySelector('script[data-site-id]') as HTMLScriptElement;
    const siteId: string = scriptTag?.getAttribute('data-site-id') || 'UNKNOWN';
    const endpoint: string = "https://localhost:8443/api/v1/analytics";

    console.log(`%c ⚡ Flowstate: Tracking actief voor ${siteId}`, "color: #42b883; font-weight: bold;");

    const track = (eventName: string, description: string = ""): void => {
        const payload: AnalyticsPayload = {
            visitorId: crypto.randomUUID(),
            url: window.location.href,
            referrer: document.referrer || "direct",
            siteId: siteId,
            eventName: eventName,
            description: description
        };

        fetch(endpoint, {
            method: "POST",
            headers: { "Content-Type": "application/json" },
            body: JSON.stringify(payload),
            mode: 'cors'
        }).catch(err => console.error("Flowstate Error:", err));
    };

    track("page_view", "Initial page load");

    let lastUrl = location.href;
    const observer = new MutationObserver(() => {
        if (location.href !== lastUrl) {
            lastUrl = location.href;
            track("page_view", `Navigated to ${window.location.pathname}`);
        }
    });
    observer.observe(document, { subtree: true, childList: true });

    document.addEventListener('click', (e) => {
        const target = e.target as HTMLElement;
        const clickable = target.closest('button, a');
        if (clickable) {
            const label = clickable.textContent?.trim().substring(0, 30) || "unnamed";
            track("click", `Interacted with: ${label}`);
        }
    });

    (window as any).flowstate = { track };
})();