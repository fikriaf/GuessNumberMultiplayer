# GuessNumberMultiplayer

[![Java](https://img.shields.io/badge/Java-8%2B-orange.svg)](https://www.oracle.com/java/)
[![Swing](https://img.shields.io/badge/GUI-Swing-blue.svg)](https://docs.oracle.com/javase/tutorial/uiswing/)
[![License](https://img.shields.io/badge/License-MIT-green.svg)](LICENSE)
[![Status](https://img.shields.io/badge/Status-Active-brightgreen.svg)]()
[![Platform](https://img.shields.io/badge/Platform-Windows%20%7C%20Linux%20%7C%20macOS-lightgrey.svg)]()

A multiplayer number guessing game built with Java Swing, featuring client-server architecture with admin panel for monitoring gameplay.

## 🚀 Features

- **Multiplayer Gaming**: Real-time client-server gameplay
- **Admin Panel**: Monitor connected players and game statistics
- **Modern GUI**: Clean Swing interface with dark/light theme toggle
- **Real-time Monitoring**: Live player tracking and game logs
- **Admin Authentication**: Secure admin access with password protection
- **Network Support**: TCP socket-based communication
- **Game Statistics**: Player performance tracking and logs

## 📋 Requirements

- Java 8 or higher
- Network connectivity for multiplayer functionality

## 🛠️ Installation

1. **Clone the repository**
   ```bash
   git clone https://github.com/yourusername/GuessNumberMultiplayer.git
   cd GuessNumberMultiplayer
   ```

2. **Compile the Java files**
   ```bash
   javac *.java
   ```

3. **Run the main application**
   ```bash
   java GuessNumber
   ```

## 🎯 Usage

### Starting the Game

1. **Launch Main Menu**: Run `java GuessNumber`
2. **Choose Mode**:
   - **Admin**: Monitor gameplay (Password: `admin123`)
   - **Server (Host)**: Host a game session
   - **Client (Guest)**: Join an existing game

### Game Flow

1. **Server Setup**: Host sets a secret number (1-100)
2. **Client Connection**: Players connect using server IP
3. **Gameplay**: Players have 10 attempts to guess the number
4. **Admin Monitoring**: Real-time player tracking and statistics

## 📁 Project Structure

```
GuessNumberMultiplayer/
├── GuessNumber.java      # Main menu and launcher
├── ServerGUI.java        # Game server implementation
├── ClientGUI.java        # Player client interface
├── AdminGUI.java         # Admin monitoring panel
└── README.md            # Project documentation
```

## 🖼️ Screenshots

*Screenshots will be added soon...*

## 🎮 Game Rules

- Players must guess a number between 1-100
- Maximum 10 attempts per game
- Server provides "too high" or "too low" hints
- Real-time feedback and scoring

## 🔧 Technical Details

- **Architecture**: Client-Server TCP Socket Communication
- **GUI Framework**: Java Swing
- **Port**: Default 1234 (configurable)
- **Threading**: Multi-threaded for concurrent client handling

## 🤝 Contributing

1. Fork the repository
2. Create a feature branch (`git checkout -b feature/AmazingFeature`)
3. Commit your changes (`git commit -m 'Add some AmazingFeature'`)
4. Push to the branch (`git push origin feature/AmazingFeature`)
5. Open a Pull Request

## 📝 License

This project is licensed under the MIT License - see the [LICENSE](LICENSE) file for details.

## 👨‍💻 Author

**Your Name**
- GitHub: [@fikriaf](https://github.com/fikriaf)
- Email: your.email@example.com

## 🙏 Acknowledgments

- Java Swing Documentation
- Socket Programming Tutorials
- Community feedback and contributions

---

⭐ **Star this repository if you found it helpful!**

