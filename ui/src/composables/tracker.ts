interface AnalyticsPayload {
    visitorId: string | null;
    path: string;
    referrer: string;
    siteId: string;
    eventName: string;
    description: string;
    browser: string;
    os: string;
    device: string;
}

(function () {
    const siteId: string = location.host;
    const endpoint: string = "https://localhost:8443/api/v1/analytics";
    const getVisitorId = () => localStorage.getItem("user_id") || "anonymous";
    console.log(`%c ⚡ Flowstate: Tracking actief voor ${siteId}`, "color: #42b883; font-weight: bold;");

    const parseUserAgent = () => {
        const ua = navigator.userAgent;
        let browser = "Unknown Browser";
        let os = "Unknown OS";
        let device = "Desktop";

        if (/windows phone/i.test(ua)) os = "Windows Phone";
        else if (/win/i.test(ua)) os = "Windows";
        else if (/android/i.test(ua)) os = "Android";
        else if (/mac/i.test(ua)) os = "macOS";
        else if (/linux/i.test(ua)) os = "Linux";
        else if (/iphone|ipad|ipod/i.test(ua)) os = "iOS";

        if (/opera|opr/i.test(ua)) browser = "Opera";
        else if (/edg/i.test(ua)) browser = "Edge";
        else if (/chrome|crios/i.test(ua)) browser = "Chrome";
        else if (/firefox|fxios/i.test(ua)) browser = "Firefox";
        else if (/safari/i.test(ua)) browser = "Safari";
        else if (/trident|msie/i.test(ua)) browser = "Internet Explorer";

        if (/tablet|ipad|playbook|silk/i.test(ua)) {
            device = "Tablet";
        } else if (/mobile|iphone|ipod|blackberry|iemobile|kindle/i.test(ua)) {
            device = "Mobile";
        } else if (/android/i.test(ua) && !/mobile/i.test(ua)) {
            device = "Tablet";
        }

        return { browser, os, device };
    };

    const clientSpecs = parseUserAgent();

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
            description: description,
            browser: clientSpecs.browser,
            os: clientSpecs.os,
            device: clientSpecs.device
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
            track("page_view", `Mapsd to ${window.location.pathname}`);
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