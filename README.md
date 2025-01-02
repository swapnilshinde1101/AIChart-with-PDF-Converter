# AI Chat Application with PDF Converter

This is a Java-based AI Chat Application integrated with PDF conversion functionality. The application allows users to interact with a chatbot and provides file-handling capabilities like converting text files to PDFs and vice versa. It uses Swing for the graphical user interface (GUI), Apache PDFBox for PDF processing, and other essential libraries for functionality.

## Features

- **Chatbot Interaction**: Communicates with users through a chatbot that provides responses based on user input.
- **Text-to-PDF Conversion**: Converts plain text files (`.txt`) into formatted PDF documents.
- **PDF-to-Text Conversion**: Extracts text from PDF files and saves them as `.txt` files.
- **File Upload**: Allows users to upload text or PDF files for processing.
- **Suggestion Panel**: Displays suggested questions for easy interaction with the chatbot.
- **History**: Keeps a history of the chat and allows users to view previous conversations.
- **Modular File Handling**: Users can select the destination for saving PDF or text files.

## Technologies Used

- **Java**: Core programming language for building the application.
- **Swing**: For creating the graphical user interface (GUI).
- **Apache PDFBox**: For handling PDF documents, including converting text to PDFs and extracting text from PDFs.
- **Maven**: For dependency management and project builds.
- **Unirest**: For making HTTP requests and interacting with APIs (if applicable).
- **JSON**: For handling data in JSON format, such as parsing responses from APIs.
- **JTextPane**: For displaying formatted text, especially useful for displaying chat content in the chat window.
- **StyledDocument**: For managing the text and style formatting of `JTextPane` components in the UI.
- **File Handling (Java I/O)**: For handling file input and output operations, such as reading and writing text files or PDFs.
- **JOptionPane**: For displaying dialog boxes, such as error messages, warnings, and user prompts.

## Installation

### Prerequisites

Make sure you have the following installed:
- **Java**: Version 8 or above
- **Maven**: For managing dependencies and building the project

### Steps to Install

1. Clone the repository:

    ```bash
    git clone https://github.com/swapnilshinde1101/AIChart-with-PDF-Converter.git
    ```

2. Navigate into the project directory:

    ```bash
    cd AIChart-with-PDF-Converter
    ```

3. Build the project using Maven:

    ```bash
    mvn clean install
    ```

4. Run the application:

    ```bash
    mvn exec:java
    ```


    

## Project Structure

Here’s the basic structure of the project:

. ├── com │ ├── aichat │ │ ├── ui │ │ │ ├── ChatWindow.java │ │ │ ├── LoadingIndicator.java │ │ ├── utils │ │ │ ├── FileConverter.java │ │ │ ├── FileHandler.java │ │ ├── chatbot │ │ │ ├── Chatbot.java │ ├── main │ │ ├── MainApp.java └── resources ├── images └── app_logo.png



## Usage

- **Chat Window**: The application opens a chat window where you can type questions or messages, and the chatbot will respond accordingly.
- **Text-to-PDF**: You can select a `.txt` file and convert it into a `.pdf` file.
- **PDF-to-Text**: You can upload a `.pdf` file and extract the text content, saving it as a `.txt` file.

## Screenshots

![Chat Window](screenshots/chat_window._Description: The main interface of the AI chat window._![Screenshot 2025-01-02 211256](https://github.com/user-attachments/assets/1eeda506-a569-44b5-ab52-3ca6d27cad52)
png)


![Screenshot 2025-01-02 211756](https://github.com/user-attachments/assets/a7b40aa1-415e-435c-89e9-f3dbb426c02e)

2. 
![Text to PDF Conversion](screenshots/text_to_pdf.png)
_Description: The Text to PDF Conver sion window._ ![Screenshot 2025-01-02 2119.com/user-attachments/assets/05e658f6-2d3f-4b02-9e67-1b3fab2eb6b2)
sion window._

## Contributing

If you'd like to contribute to this project, feel free to fork the repository, make changes, and submit a pull request.

## License

This project is licensed under the MIT License - see the [LICENSE](LICENSE) file for details.

## Contact

For any questions or suggestions, feel free to reach out to:

- **Swapnil Shinde**: [swapnilshinde1101@gmail.com](mailto:swapnilshinde1101@gmail.com)
