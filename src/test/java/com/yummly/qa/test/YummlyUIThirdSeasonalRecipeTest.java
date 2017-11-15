package com.yummly.qa.test;

import java.util.concurrent.TimeUnit;
import java.util.List;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;

public class YummlyUIThirdSeasonalRecipeTest {
	private WebDriver driver;
	
	@Before
	public void setUp()
	{
		this.driver = new FirefoxDriver();
		this.driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
	}
	
	@After
	public void tearDown()
	{
		this.driver.close();
	}
	
	/**
	 * This will go to https://www.yummly.com/
	 * Go to Seasonal tab, Find the 3rd recipe, click on the 3rd recipe
	 * @result Will successfully pass if the title of the 3rd recipe on
	 *         the Seasonal page is the same as the title after clicking
	 *         to expand to see the entire recipe.
	 */
	@Test
	public void YummlyUIClickThirdSeasonalRecipeTest()
	{
		this.driver.get("https://www.yummly.com/");
		this.driver.findElement(By.linkText("Seasonal")).click();

		// Grabbing all recipes on the page
		List<WebElement> elements = this.driver.findElements(By.cssSelector("div.recipe-card.single-recipe.visible"));

		// Specifically check if have at least three recipes -- else fail
		// Obtain the title of the third recipe
		// click on the image link of the third recipe 
		// and verify title matches title on recipe page
		if (elements.size() > 2) {
			WebElement element = elements.get(2);
			String recipeTitle =  element.findElement(By.className("card-title")).getText();
			element.findElement(By.className("image-overlay")).click();
			element = this.driver.findElement((By.className("primary-info-text")));
			String recipePageTitle = element.findElement(By.cssSelector("h1")).getText();
			//System.out.println(recipeTitle);
			//System.out.println(recipePageTitle);
			Assert.assertEquals(recipeTitle, recipePageTitle);
		}
		else {
			Assert.fail("There was not three recipes under Seasonal.");
		}

	} 
}