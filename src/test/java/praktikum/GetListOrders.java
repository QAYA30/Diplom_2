package praktikum;

import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.Response;
import org.hamcrest.MatcherAssert;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import praktikum.pojo.AuthorizationClient;
import praktikum.pojo.CreateOrder;
import praktikum.pojo.UserCreation;
import java.util.ArrayList;
import java.util.List;
import static org.apache.http.HttpStatus.SC_OK;
import static org.apache.http.HttpStatus.SC_UNAUTHORIZED;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertEquals;
import static praktikum.ClientBurger.*;
import static praktikum.pojo.UserCreation.getRandomUser;

public class GetListOrders {
    UserCreation userCreation;
    String token;
    String fluorescentBun = "61c0c5a71d1f82001bdaaa6d";
    String biocutlet = "61c0c5a71d1f82001bdaaa71";
    String sauceSpicy = "61c0c5a71d1f82001bdaaa72";

    @Before
    public void init() {
        userCreation = getRandomUser();
        sucUserReg(userCreation);
        AuthorizationClient authorizationClient = new AuthorizationClient(userCreation.getEmail(), userCreation.getPassword());
        Response responseAuth = authUserReg(authorizationClient);
        token = responseAuth.body().jsonPath().getString("accessToken");
        List<String> ingredients = new ArrayList<>();
        ingredients.add(fluorescentBun);
        ingredients.add(biocutlet);
        ingredients.add(sauceSpicy);
        CreateOrder createOrder = new CreateOrder(ingredients);
        orderCreationAuth(createOrder, token);
    }
    @Test
    @DisplayName("Получить список заказов авторизованного пользователя")
    @Description("Проверка, что авторизованный пользователь получит список заказов")
    public void getListOrdersAuthUserTest() {
        Response resOrders = ordersListAuth(token);
        resOrders.then().assertThat().statusCode(200);
        MatcherAssert.assertThat(resOrders.body().jsonPath().getList("orders"), notNullValue());
    }
    @Test
    @DisplayName("Отсутстве списка заказов у неаворизованного пользователя")
    @Description("Неавторизованный пользователь не может получить список заказов")
    public void getListOrdersNoAuthUserTest() {
        Response resOrders = ordersListNoAuth();
        assertEquals(SC_UNAUTHORIZED, resOrders.statusCode());
        assertEquals("false", resOrders.body().jsonPath().getString("success"));
        assertEquals("You should be authorised", resOrders.body().jsonPath().getString("message"));
    }
    @After
    public void clear() {
        if (token != null) {
            deleteUser(token);
        }
    }
}
