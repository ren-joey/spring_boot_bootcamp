# How To Use

## Dependencies Preview
### System
- Tomcat
- JAVA Spring Boot v3.3.5
- Maven v3.8.x
- JAVA JDK 21
- MariaDB

### Software
- cAdvisor - docker status gathering tool
- Prometheus - log integration
- Grafana - log visualization
- Grafana K6 - Stress test

## Installation
You have to install Docker for the project operations.
The requirements list below:
- For Windows
    - Docker Desktop
    - WSL
- For UNIX
    - Docker

### Set System Environments
Copy the `.env.example` and rename it as `.env`

### Start The Project
```bash
docker compose up --build -d
```

### Access The Service
Once all dependent container are started, you may access the service by [http://localhost](http://localhost) <br>
If you are using `Postman v2.1^` to proceed the test, you may use the [configuration](./postman/Next.js.postman_collection.json) to import the APIs at once.
|Method|API|Payload|Header|
|---|---|---|---|
|GET|{{base_url}}/orders|{}|{}|
|POST|{{base_url}}/orders/create|{"name": "test", "price": 1000}|{"Authorization" : "Bearer <JWT_TOKEN>"}|
|PUT|{{base_url}}/orders/:id|{"orderId": "66e725481052fd75723c077f", "name": "test2"}|{"Authorization" : "Bearer <JWT_TOKEN>"}|
|DEL|{{base_url}}/orders/:id|{"orderId": "66e8541650c76f3b8027473f"}|{"Authorization" : "Bearer <JWT_TOKEN>"}|
|POST|{{base_url}}/auth/register|{"email": "user@gmail.com", "name": "root", "password": "123456789"}|{}|
|POST|{{base_url}}/auth/login|{"email": "user@gmail.com", "password": "123456789"}|{}|

### Stress Test
```bash
cat ./k6/script.js | docker run --rm -i --network host grafana/k6 run -
```

### Stop and Remove The Container
```bash
docker compose down -v
```

### Append Your Test Script (optional)
You can append the test script from `k6/scripts.js`
```js
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
```

### Build Package
```bash
mvn package
```
If you look in the target directory, you should see `myproject-0.0.1-SNAPSHOT.jar`. The file should be around 18 MB in size. If you want to peek inside, you can use `jar tvf`, as follows:
```bash
jar tvf target/myproject-0.0.1-SNAPSHOT.jar
```
To run that application, use the `java -jar` command, as follows:
```bash
java -jar target/myproject-0.0.1-SNAPSHOT.jar
```

