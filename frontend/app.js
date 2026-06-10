const formLance = document.getElementById("form-lance");
const listaLances = document.getElementById("lista-lances");
const filtroLeilao = document.getElementById("filtro-leilao");
const btnFiltrar = document.getElementById("btn-filtrar");
const btnLimpar = document.getElementById("btn-limpar");

const formatadorMoeda = new Intl.NumberFormat("pt-BR", {
    style: "currency",
    currency: "BRL",
});

function formatarDataHora(iso) {
    return new Date(iso).toLocaleString("pt-BR");
}

function renderizarLances(lances) {
    if (!lances.length) {
        listaLances.innerHTML =
            '<tr class="vazio"><td colspan="5">Nenhum lance ainda.</td></tr>';
        return;
    }

    const ordenados = [...lances].sort((a, b) => b.id - a.id);

    listaLances.innerHTML = ordenados
        .map(
            (lance) => `
            <tr>
                <td>${lance.id}</td>
                <td>${lance.leilaoId}</td>
                <td>${lance.usuario}</td>
                <td>${formatadorMoeda.format(lance.valor)}</td>
                <td>${formatarDataHora(lance.dataHora)}</td>
            </tr>`
        )
        .join("");
}

async function carregarLances(leilaoId) {
    const url = leilaoId ? `/api/lances?leilaoId=${leilaoId}` : "/api/lances";
    const resp = await fetch(url);
    if (!resp.ok) {
        throw new Error("HTTP " + resp.status);
    }
    const lances = await resp.json();
    renderizarLances(lances);
}

formLance.addEventListener("submit", async (evento) => {
    evento.preventDefault();

    const corpo = {
        leilaoId: Number(document.getElementById("leilaoId").value),
        usuario: document.getElementById("usuario").value.trim(),
        valor: Number(document.getElementById("valor").value),
    };

    try {
        const resp = await fetch("/api/lances", {
            method: "POST",
            headers: { "Content-Type": "application/json" },
            body: JSON.stringify(corpo),
        });

        const data = await resp.json();

        if (!resp.ok) {
            const detalhes = data.detalhes ? data.detalhes.join("\n") : JSON.stringify(data);
            alert("Erro " + resp.status + ":\n" + detalhes);
            return;
        }

        alert(
            "Lance #" + data.id + " criado!\n" +
            "Leilao: " + data.leilaoId + "\n" +
            "Usuario: " + data.usuario + "\n" +
            "Valor: " + formatadorMoeda.format(data.valor)
        );
        formLance.reset();
        await carregarLances(filtroLeilao.value || null);
    } catch (err) {
        alert("Falha: " + err.message);
    }
});

btnFiltrar.addEventListener("click", () => {
    carregarLances(filtroLeilao.value || null);
});

btnLimpar.addEventListener("click", () => {
    filtroLeilao.value = "";
    carregarLances(null);
});

carregarLances(null).catch((err) =>
    console.error("Nao foi possivel carregar os lances:", err)
);

const socket = new WebSocket(
    "ws://localhost:8080/ws/lances"
);

socket.onmessage = (event) => {

    console.log(
        "Novo lance recebido:",
        event.data
    );

    carregarLances(
        filtroLeilao.value || null
    );
};