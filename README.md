# TelloToInsta

TelloToInsta is a Tellonym Tells Instagram exporter tool written in Kotlin. This tool allows you to export your Tellonym tells and post them on Instagram seamlessly. With TelloToInsta, you can effortlessly share the content from your Tellonym account with your Instagram followers.

## Table of Contents

- [Introduction](#introduction)
- [Features](#features)
- [Requirements](#requirements)
- [Installation](#installation)
- [Usage](#usage)
- [Contributing](#contributing)
- [License](#license)

## Introduction

Tellonym is a social networking platform that enables users to receive anonymous messages and questions from their followers. TelloToInsta provides a convenient way to export these tells and publish them on your Instagram account. It aims to simplify the process of sharing engaging content with your Instagram audience while leveraging the anonymity of Tellonym.

## Features

- Export Tellonym tells to Instagram posts
- Customize the appearance of exported posts
- Support for text and image tells
- Generate hashtags based on the content of the tells
- Convenient command-line interface (CLI)

## Requirements

To use TelloToInsta, you need the following requirements:

- Java Development Kit (JDK) 19 or higher
- Kotlin compiler
- Instagram account
- Tellonym account

## Installation

1. Clone the TelloToInsta repository:

   ```shell
   git clone https://github.com/your-username/tellotoinsta.git
   ```

2. Build the project using Gradle:

   ```shell
   cd tellotoinsta
   ./gradlew build
   ```

3. The build process will generate an executable JAR file in the `build/libs` directory.

## Usage

To use TelloToInsta, follow these steps:

1. Run the TelloToInsta JAR file:

   ```shell
   java -jar tellotoinsta.jar
   ```

2. Follow the interactive prompts to configure your Tellonym and Instagram accounts.

3. Choose the desired Tellonym tells you want to export.

4. Customize the appearance of the exported posts, such as adding captions, hashtags, or applying filters.

5. TelloToInsta will automatically generate Instagram posts based on the selected tells.

6. Review and confirm the generated posts before publishing them on Instagram.

7. The exported posts will be uploaded to your Instagram account.

Please note that TelloToInsta relies on the Instagram API for posting content, so you may need to obtain relevant API credentials or tokens for successful execution.

## Contributing

Contributions to TelloToInsta are welcome! If you have any ideas, bug fixes, or feature enhancements, feel free to submit a pull request. Please make sure to follow the existing coding style and include appropriate tests for your changes.

## License

TelloToInsta is licensed under the [MIT License](LICENSE). Feel free to modify and distribute the code as per the terms of the license.
