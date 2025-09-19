package org.example;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.concurrent.TimeUnit;

public class StakeWheelAutomation {

    public static void main(String[] args) {
        System.setProperty("webdriver.chrome.driver", "C:\\Users\\13nag\\OneDrive\\Desktop\\Drivechrome\\chromedriver.exe");
        System.out.println("Starting WebDriver...");

        ChromeOptions options = new ChromeOptions();
        options.setExperimentalOption("debuggerAddress", "localhost:9111");

        WebDriver driver = new ChromeDriver(options);
        System.out.println("WebDriver initialized.");

        if (driver == null) {
            System.out.println("Failed to create WebDriver instance.");
            return;
        }

        System.out.println("Connected to existing Chrome session.");

        double initialBetAmount = 0.01;
        double betAmount = initialBetAmount;
        int maxLosses = 15;
        int iterations = 0;
        int lossesSinceLastDouble = 0;
        double previousBalance = 0;

        driver.get("https://stake.us/casino/games/wheel");
        System.out.println("Navigated to the game page.");

        try {
            Thread.sleep(7000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }


        WebElement riskSelectElement = driver.findElement(By.cssSelector("select[data-test='risk-select']"));
        WebElement highRiskOption = riskSelectElement.findElement(By.xpath(".//option[@value='high']"));
        highRiskOption.click();

        while (true) {
            iterations++;
            System.out.println("Iteration: " + iterations);

            int lossCount = 0;
            boolean hasWon = false;

            while (lossCount < maxLosses && !hasWon) {


                WebElement amountInput = driver.findElement(By.cssSelector("input[data-test='input-game-amount']"));
                amountInput.clear();
                amountInput.sendKeys(String.valueOf(betAmount));

                try {
                    Thread.sleep(3000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                WebElement playButton = driver.findElement(By.cssSelector("button[data-test='bet-button']"));
                playButton.click();

                WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(8));
                WebElement balanceElement = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@id=\"svelte\"]/div[2]/div/div[3]/div/div/div/div[2]/div/div/div/button/div/div/span[1]/span")));

                String balanceText = balanceElement.getText();
                double currentBalance = Double.parseDouble(balanceText.replaceAll("[^\\d.]", ""));
                try {
                    Thread.sleep(7000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                if (previousBalance == 0) {
                    previousBalance = currentBalance;
                } else {
                    if (currentBalance < previousBalance) {
                        lossCount++;
                        lossesSinceLastDouble++;
                        System.out.println("Loss count: " + lossCount);
                        previousBalance = currentBalance; // Update previousBalance after a loss
                    } else if (currentBalance > previousBalance) {
                        betAmount = initialBetAmount;
                        lossesSinceLastDouble = 0;
                        System.out.println("Won! Resetting bet amount.");
                        previousBalance = currentBalance; // Update previousBalance after a win
                        break;
                    }
                }
            }

            if (lossesSinceLastDouble >= maxLosses) {

                try {
                    Thread.sleep(6000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                WebElement doubleButton = driver.findElement(By.xpath("//*[@id=\"main-content\"]/div/div[1]/div[1]/label/div/div[2]/button[2]"));
                doubleButton.click();
                System.out.println("Reached maximum losses. Pressed 2x button.");
                betAmount *= 2;
                lossesSinceLastDouble = 0;
                System.out.println("Bet amount doubled to: " + betAmount);
            }

            if (iterations >= 9999) {
                System.out.println("Exiting loop after 10 iterations.");
                break;
            }
        }

        driver.quit();
        System.out.println("WebDriver closed.");
    }
}

