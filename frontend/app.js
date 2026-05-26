// Entrega 1: testar comunicacao HTML -> servidor.
// Sera expandido nas entregas seguintes (form de lance, WebSocket, etc).

const btnPing = document.getElementById("btn-ping");
const respostaPing = document.getElementById("resposta-ping");

btnPing.addEventListener("click", async () => {
    respostaPing.textContent = "Chamando GET /api/ping ...";
    try {
        const resp = await fetch("/api/ping");
        if (!resp.ok) {
            throw new Error("HTTP " + resp.status);
        }
        const data = await resp.json();
        respostaPing.textContent = JSON.stringify(data, null, 2);
    } catch (err) {
        respostaPing.textContent = "Falha: " + err.message;
    }
});
