package praktikum;

import io.qameta.allure.Step;
import io.restassured.response.Response;
import java.util.Map;
import static io.restassured.RestAssured.given;

import praktikum.pojo.AuthorizationClient;
import praktikum.pojo.CreateOrder;
import praktikum.pojo.RefreshToken;
import praktikum.pojo.UserCreation;


public class ClientBurger extends praktikum.BaseApiClient {
    @Step("Регистрация клиента")
    public static Response sucUserReg(UserCreation userCreation) {
        return (Response) given()
                .spec(getSeqSpec())
                .body(userCreation)
                .when()
                .post(BASE_URL + "/api/auth/register")
                .then().log().all().extract();
    }
    @Step("Авторизация Юзера")
    public static Response authUserReg(AuthorizationClient authorizationClient) {
        return (Response) given()
                .spec(getSeqSpec())
                .body(authorizationClient)
                .when()
                .post(BASE_URL + "/api/auth/login")
                .then().log().all().extract();
    }
    @Step("Удаление Юзера")
    public static Boolean deleteUser(String token) {
        return given()
                .spec(getSeqSpec())
                .headers(Map.of("authorization", token))
                .when()
                .delete(BASE_URL + "/api/auth/user")
                .then()
                .assertThat().log().all()
                .statusCode(202)
                .extract()
                .path("ok");
    }
    @Step("Обновление данных Юзера с авторизацией")
    public static Response updateUserWithAuth(UserCreation userCreation, String token) {
        return (Response) given()
                .spec(getSeqSpec())
                .headers(Map.of("authorization", token))
                .body(userCreation)
                .when()
                .patch(BASE_URL + "/api/auth/user").then().log().all().extract();
    }
    @Step("Обновление токена")
    public static Response updateTokenUser(RefreshToken refreshToken) {
        return (Response) given()
                .spec(getSeqSpec())
                .body(refreshToken)
                .when()
                .post(BASE_URL + "/api/auth/token").then().log().all().extract();
    }
    @Step("Обновление данных Юзера без авторизаци")
    public static Response updateUserNoAuth(UserCreation userCreation) {
        return (Response) given()
                .spec(getSeqSpec())
                .body(userCreation)
                .when()
                .patch(BASE_URL + "/api/auth/user").then().log().all().extract();
    }
    @Step("Создание заказа с авторизацией")
    public static Response orderCreationAuth(CreateOrder createOrder, String token) {
        return (Response) given()
                .spec(getSeqSpec())
                .headers(Map.of("authorization", token))
                .body(createOrder)
                .when()
                .post(BASE_URL + "/api/orders").then().log().all().extract();
    }
    @Step("Создание заказа без авторизации")
    public static Response orderCreationNoAuth(CreateOrder createOrder) {
        return (Response) given()
                .spec(getSeqSpec())
                .body(createOrder)
                .when()
                .post(BASE_URL + "/api/orders").then().log().all().extract();
    }
    @Step("Получить список заказов c авторизацие")
    public static Response ordersListAuth(String token) {
        return (Response) given()
                .spec(getSeqSpec())
                .headers(Map.of("authorization", token))
                .when()
                .get(BASE_URL + "/api/orders").then().log().all().extract();
    }
    @Step("Получить список заказов без авторизации")
    public static Response ordersListNoAuth() {
        return (Response) given()
                .spec(getSeqSpec())
                .when()
                .get(BASE_URL + "/api/orders").then().log().all().extract();
    }
}
