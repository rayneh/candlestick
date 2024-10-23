# Candlestick Chart Example

This project demonstrates how to ingest price reports via WebSocket and convert them into candlestick chart data.

## Features

- Connects to a WebSocket for real-time price updates
- Processes price updates and stores them in Open High Low Close (OHLC) format
- Supports multiple timeframes: 1m, 2m, 5m, 15m, 30m, 1h, 2h, 4h, 6h, 12h, 1d, 1w

## Prerequisites

- Java 11 or higher
- Maven

## Getting Started

1. Clone the repository:
    ```bash
   git clone https://YOUR_GITHUB_TOKEN@github.com/rayneh/candlestick.git

2. Navigate to the project directory:
    ```bash
   cd candlestick

3. Build the project using Maven:
    ```bash
   mvn clean install

4. Run the application:
    ```bash
    mvn exec:java -Dexec.mainClass="org.example.Main"
   
## Usage
Once the application is running, it will listen for price updates and process them into candlestick data.

## License
This project is licensed under the [MIT License](https://opensource.org/licenses/MIT) see the LICENSE file for details.