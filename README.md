# Stake Wheel Automation – Martingale Strategy Simulation
Overview

This Java project automates playing the Stake Wheel game using Selenium WebDriver and applies the Martingale betting strategy.

It connects to an existing Chrome session running on --remote-debugging-port=9111.

The bot automatically places bets, monitors wins/losses, and doubles the bet after consecutive losses according to Martingale rules.

The goal is to simulate whether a profit can be made on Stake given a small starting balance.

## Features

Automates Stake Wheel gameplay using Selenium.

Implements Martingale betting strategy:

On a loss, the bet is doubled.

On a win, the bet resets to the base amount.

Configurable parameters:

initialBetAmount – starting bet.

maxLosses – number of consecutive losses before doubling.

iterations – maximum number of spins to simulate.

Tracks statistics including:

Current balance

Loss count

Bet amount adjustments

## Requirements

Java JDK 11+

Selenium WebDriver

Chrome browser installed

ChromeDriver executable compatible with your Chrome version

Run Chrome with remote debugging enabled

## License
This project is educational and does not promote real-money gambling. 
