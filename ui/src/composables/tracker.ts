interface AnalyticsPayload {
    visitorId: string | null;
    path: string;
    referrer: string;
    siteId: string;
    eventName: string;
    description: string;
}

(function () {
    const scriptTag = document.querySelector('script[data-site-id]') as HTMLScriptElement;
    const siteId: string = location.host;
    const endpoint: string = "https://localhost:8443/api/v1/analytics";
    const getVisitorId = () => localStorage.getItem("user_id") || "anonymous";
    console.log(`%c ⚡ Flowstate: Tracking actief voor ${siteId}`, "color: #42b883; font-weight: bold;");

    const sendData = (payload: AnalyticsPayload) => {
        const blob = new Blob([JSON.stringify(payload)], {type: "application/json"});

        const success = navigator.sendBeacon(endpoint, blob);

        if (!success) {
            fetch(endpoint, {
                method: "POST",
                body: JSON.stringify(payload),
                headers: {"Content-Type": "application/json"},
                keepalive: true
            });
        }
    };

    const track = (eventName: string, description: string = ""): void => {
        const payload: AnalyticsPayload = {
            visitorId: getVisitorId(),
            path: location.pathname,
            referrer: document.referrer || "direct",
            siteId: siteId,
            eventName: eventName,
            description: description
        };

        sendData(payload);
    };

    // window.addEventListener('visibilitychange', () => {
    //     if (document.visibilityState === 'hidden') {
    //         track("exit", "User left the page or closed tab");
    //     }
    // });

    track("page_view", "Initial page load");

    let lastUrl = location.href;
    const observer = new MutationObserver(() => {
        if (location.href !== lastUrl) {
            lastUrl = location.href;
            track("page_view", `Navigated to ${window.location.pathname}`);
        }
    });
    observer.observe(document, { subtree: true, childList: true });

    // document.addEventListener('click', (e) => {
    //     const target = e.target as HTMLElement;
    //     const clickable = target.closest('button, a');
    //     if (clickable) {
    //         const label = clickable.textContent?.trim().substring(0, 30) || "unnamed";
    //         track("click", `Interacted with: ${label}`);
    //     }
    // });

    (window as any).flowstate = { track };
})();