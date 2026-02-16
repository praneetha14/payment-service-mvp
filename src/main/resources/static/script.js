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
window.loadPayments = async function () {
    const token = localStorage.getItem("token");

    if (!token) {
        window.location.href = "login.html";
        return;
    }

    const response = await fetch(API_BASE + "/get", {
        headers: { "Authorization": "Bearer " + token }
    });

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

    const response = await fetch(API_BASE + "/payment", {
        method: "POST",
        headers: { "Content-Type": "application/json" },
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
