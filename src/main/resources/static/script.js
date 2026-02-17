const API_BASE = "http://localhost:8080/api/v1/payments";
const AUTH_URL = "http://localhost:8080/auth/login";

// ---------------- LOGIN ----------------
window.login = async function () {
    const username = document.getElementById("username").value;
    const password = document.getElementById("password").value;

    const response = await fetch(
        `${AUTH_URL}?username=${username}&password=${password}`,
        { method: "POST" }
    );

    if (!response.ok) {
        document.getElementById("error").innerText = "Invalid credentials";
        return;
    }

    const data = await response.json();
    localStorage.setItem("token", data.token);
    window.location.href = "payments.html";
};

// ---------------- PAYMENTS PAGE ----------------
let currentPage = 0;
let limit = 5;
let totalPages = 0;

window.loadPayments = async function () {
    const token = localStorage.getItem("token");

    if (!token) {
        window.location.href = "login.html";
        return;
    }

    const response = await fetch(
        `${API_BASE}/get?page=${currentPage}&limit=${limit}`,
        {
            headers: { "Authorization": "Bearer " + token }
        }
    );

    const result = await response.json();
    const table = document.getElementById("paymentsTable");
    table.innerHTML = "";

    result.data.forEach(p => {
        table.innerHTML += `
            <tr>
                <td>${p.name}</td>
                <td>${p.email}</td>
                <td>${p.amount}</td>
                <td>${p.status}</td>
            </tr>
        `;
    });

    currentPage = result.currentPage;
    totalPages = result.totalPages;

    document.getElementById("pageInfo").innerText =
        `Page ${currentPage + 1} of ${totalPages}`;

    updateButtons();
};

function updateButtons() {
    document.querySelector("button[onclick='prevPage()']").disabled =
        currentPage === 0;

    document.querySelector("button[onclick='nextPage()']").disabled =
        currentPage >= totalPages - 1;
}

window.nextPage = function () {
    if (currentPage < totalPages - 1) {
        currentPage++;
        loadPayments();
    }
};

window.prevPage = function () {
    if (currentPage > 0) {
        currentPage--;
        loadPayments();
    }
};

window.openPaymentForm = function () {
    window.location.href = "payment-form.html";
};

window.logout = function () {
    localStorage.removeItem("token");
    window.location.href = "login.html";
};

// ---------------- PAYMENT FORM ----------------
window.submitPayment = async function () {
    const payload = {
        name: document.getElementById("name").value,
        email: document.getElementById("email").value,
        phoneNumber: document.getElementById("phoneNumber").value,
        amount: parseFloat(document.getElementById("amount").value)
    };

    const token = localStorage.getItem("token");

    const response = await fetch(API_BASE + "/payment", {
        method: "POST",
        headers: {
            "Content-Type": "application/json",
            "Authorization": "Bearer " + token
        },
        body: JSON.stringify(payload)
    });

    const result = await response.json();

    if (result.success) {
        alert("Payment successful");
        window.location.href = "payments.html";
    } else {
        document.getElementById("response").innerText =
            JSON.stringify(result.errors);
    }
};

window.goBack = function () {
    window.location.href = "payments.html";
};
