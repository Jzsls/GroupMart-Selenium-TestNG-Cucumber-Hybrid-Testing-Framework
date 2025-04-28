package groupmart.Tests;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;

import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import groupmart.TestComponents.BaseTest;
import groupmart.pageobjects.MyCart;
import groupmart.pageobjects.OrdersPage;
import groupmart.pageobjects.PaymentPage;
import groupmart.pageobjects.ProductCatalogue;
import groupmart.pageobjects.ThankyouPage;

public class SubmitOrderTest extends BaseTest {

	@Test(dataProvider = "getData")
	public void submitOrder(HashMap<String, String> user) throws InterruptedException, IOException {

		addCredentials(user);

		// doing login page automation
		ProductCatalogue productCatalogue = loginPage.appLogin(user.get("email"), user.get("password"));

		// doing catalogue page automation

		// clicking on the desired product
		productCatalogue.addDesiredProductToCart(user.get("productName"));
		// checking if 'Product Added To Cart' message appears
		productCatalogue.verifyAddToCartMessageDisplay();
		// checking if loading graphics disappears
		productCatalogue.verifyLoadingAnimationDisappear();
		MyCart myCart = productCatalogue.jsCartbuttonClick();

		// doing MyCart page automation
		boolean cartProduct = myCart.verifyProductInCart(user.get("productName"));
		Assert.assertTrue(cartProduct);
		PaymentPage paymentPage = myCart.clickOnCheckout();

		// doing Payment page automation
		paymentPage.fillCardDetails(envJson.path("paymentPage").path("cardDetails"));
		paymentPage.selectShippingCountry(envJson.path("paymentPage").path("desiredCountrySearchText").asText(),
				envJson.path("paymentPage").path("desiredCountry").asText());
		ThankyouPage thankyouPage = paymentPage.clickPlaceOrderButton();

		// Confirming successful order placing on Thanks Page
		String expectedSucessOrder = thankyouPage.getThankyouMessage();
		Assert.assertTrue(expectedSucessOrder.equals(envJson.path("thankyouPage").path("succOrderText").asText()));
		Assert.assertTrue(true);

	}
	// data generating methodd
	@Test(dataProvider = "getData", dependsOnMethods = { "submitOrder" })
	public void productInOrderHistoryTest(HashMap<String, String> user) throws IOException {
		ProductCatalogue productCatalogue = loginPage.appLogin(user.get("email"), user.get("password"));
		OrdersPage ordersPage = productCatalogue.jsMyOrderClick();
		boolean productExists = ordersPage.verifyProductInOrderList(user.get("productName"));
		Assert.assertTrue(productExists);
	}

	@DataProvider
	public Object[][] getData() throws IOException {
		List<HashMap<String, String>> data = getUserJsonData(USER_PROFILES_PATH);
		return new Object[][] { { data.get(0) }, { data.get(1) } };
	}

}
