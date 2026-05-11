export function useInitialize() {
    const setUserId = () => {
        const existingUserId = localStorage.getItem("user_id");

        if(!existingUserId){
            localStorage.setItem("user_id", crypto.randomUUID());
        }
    };
    return { setUserId }
}