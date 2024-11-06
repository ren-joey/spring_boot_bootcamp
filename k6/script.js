import http from 'k6/http';
import { check, sleep } from 'k6';

export const options = {
    vus: 100,
    duration: '60s'
};

export default function () {
    const baseUrl = 'http://localhost';

    const loginRes = http.post(baseUrl + '/auth/login', JSON.stringify({
        email: 'user@gmail.com',
        password: '123456789',
    }), {
        headers: { 'Content-Type': 'application/json' },
    });

    const loginSuccess = check(loginRes, {
        'login successful': (res) => res.status === 201,
        'token received': (res) => !!res.json('access_token'),
    });

    const token = loginRes.json('access_token');

    const createOrderRes = http.post(baseUrl + '/orders', JSON.stringify({
        name: 'New Product',
        price: 99.99,
    }), {
        headers: {
            'Content-Type': 'application/json',
            'Authorization': `Bearer ${token}`,
        },
    });

    const createOrderSuccess = check(createOrderRes, {
        'order created': (res) => res.status === 201,
        'order id received': (res) => !!res.json('id'),
    });

    if (!createOrderSuccess) {
        console.error('Order creation failed');
        return;
    }

    const orderId = createOrderRes.json('id');

    const deleteOrderRes = http.del(baseUrl + '/orders/' + orderId, JSON.stringify({}), {
        headers: {
            'Content-Type': 'application/json',
            'Authorization': `Bearer ${token}`,
        },
    });

    check(deleteOrderRes, {
        'order deleted': (res) => res.status === 200,
    });

    http.get(baseUrl + '/orders');

    sleep(1);
}