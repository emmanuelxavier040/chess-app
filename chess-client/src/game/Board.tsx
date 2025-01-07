import React, { useState } from 'react';
import './ChessBoard.css';

// Type for chess pieces
interface ChessPiece {
  type: string; // 'pawn', 'rook', 'knight', 'bishop', 'queen', 'king'
  color: 'white' | 'black';
  position: string; // e.g., 'e2'
}

// Type for server message
interface Player {
  id: string;
  colour: 'white' | 'black';
  secondsLeft: number;
  online: boolean;
}

interface GameData {
  gameId: string;
  status: string;
  maxTimeInSeconds: number;
  player1: Player;
  player2: Player;
  currentPlayerId: string;
}

// Initial setup of chess pieces
const initialPieces: ChessPiece[] = [
  // Pawns
  ...Array(8)
    .fill(null)
    .map((_, i): ChessPiece => ({
      type: 'pawn',
      color: 'white',
      position: `${String.fromCharCode(97 + i)}2`, // a2, b2, ..., h2
    })),
  ...Array(8)
    .fill(null)
    .map((_, i): ChessPiece => ({
      type: 'pawn',
      color: 'black',
      position: `${String.fromCharCode(97 + i)}7`, // a7, b7, ..., h7
    })),
  // Rooks
  { type: 'rook', color: 'white', position: 'a1' },
  { type: 'rook', color: 'white', position: 'h1' },
  { type: 'rook', color: 'black', position: 'a8' },
  { type: 'rook', color: 'black', position: 'h8' },
  // Knights
  { type: 'knight', color: 'white', position: 'b1' },
  { type: 'knight', color: 'white', position: 'g1' },
  { type: 'knight', color: 'black', position: 'b8' },
  { type: 'knight', color: 'black', position: 'g8' },
  // Bishops
  { type: 'bishop', color: 'white', position: 'c1' },
  { type: 'bishop', color: 'white', position: 'f1' },
  { type: 'bishop', color: 'black', position: 'c8' },
  { type: 'bishop', color: 'black', position: 'f8' },
  // Queens
  { type: 'queen', color: 'white', position: 'd1' },
  { type: 'queen', color: 'black', position: 'd8' },
  // Kings
  { type: 'king', color: 'white', position: 'e1' },
  { type: 'king', color: 'black', position: 'e8' },
];

const ChessBoard: React.FC = () => {
  const [pieces, setPieces] = useState<ChessPiece[]>(initialPieces);
  const [selectedPiece, setSelectedPiece] = useState<ChessPiece | null>(null);
  const [socket, setSocket] = useState<WebSocket | null>(null);
  const [gameData, setGameData] = useState<GameData | null>(null);
  const [sessionId, setSessionId] = useState<String | null>(null);

  const handleJoinGame = () => {
    const newSocket = new WebSocket('ws://localhost:8080/new-game');

    newSocket.onopen = () => {
      console.log('WebSocket connection established to /new-game');
      
    };

    newSocket.onmessage = (event) => {
      const message = JSON.parse(event.data);
      console.log('Message from server:', message);

      // If the message contains game data, update state
      if (message.sessionId) {
        setSessionId(message.sessionId);
      }
      if (message.status == "Game.status.ready" || message.status == "Game.status.in_progress") {
        setGameData(message);
      }
      if (message.type === 'ChessMove') {
        const { from, to } = message;
    
        setPieces((prevPieces) =>
            prevPieces
              .filter((piece) => piece.position !== to)
              .map((piece) =>
                piece.position === from ? { ...piece, position:to } : piece 
              )
          );
      }
    };

    newSocket.onerror = (error) => {
      console.error('WebSocket error:', error);
    };

    newSocket.onclose = () => {
      console.log('WebSocket connection closed');
    };

    setSocket(newSocket);
  };

  const handleSquareClick = (position: string) => {
    console.log(position);
    if (selectedPiece) {
        if(selectedPiece.position === position) {
            setSelectedPiece(null);
            return;
        }

            
      // Move the selected piece and remove any piece at the destination
      setPieces((prevPieces) =>
        prevPieces
          .filter((piece) => piece.position !== position) // Remove the piece at the destination
          .map((piece) =>
            piece === selectedPiece ? { ...piece, position } : piece // Update the position of the selected piece
          )
      );
      setSelectedPiece(null);
  
      // Notify the server about the move
      socket?.send(
        JSON.stringify({
          type: 'Game.status.move',
          action: {
            type: "ChessMove",
            gameId: gameData?.gameId,
            from: selectedPiece.position,
            to: position,
            playerId: sessionId,
          },
        })
            //   socket?.send({"type":"Game.status.move", "action": {"type": "ChessMove", "gameId":"123",  "from": "d2", "to":"d4", "playerId":"3948db05-ad8f-7796-d54e-7aee68651c57"}})

      );
    } else {
      // Select a piece
      const piece = pieces.find((p) => p.position === position);
      if (piece) setSelectedPiece(piece);
    }
  };
  
  return (
    <div className="chess-board-container">
      <button className="join-button" onClick={handleJoinGame}>
        Join Game
      </button>
      {gameData && (
        <div className="game-info">
          <h2>Game Info</h2>
          <p>Game ID: {gameData.gameId}</p>
          <p>Status: {gameData.status}</p>
          <p>Max Time: {gameData.maxTimeInSeconds} seconds</p>
          <p>
            Player 1 ({gameData.player1.colour}):{' '}
            {gameData.player1.online ? 'Online' : 'Offline'} - Time Left:{' '}
            {gameData.player1.secondsLeft}s
          </p>
          <p>
            Player 2 ({gameData.player2.colour}):{' '}
            {gameData.player2.online ? 'Online' : 'Offline'} - Time Left:{' '}
            {gameData.player2.secondsLeft}s
          </p>
          <p>
            Current Turn: {gameData.currentPlayerId === gameData.player1.id ? 'Player 1' : 'Player 2'}
          </p>
        </div>
      )}
      <div className="chess-board">
        {Array(8)
          .fill(null)
          .map((_, row) =>
            Array(8)
              .fill(null)
              .map((_, col) => {
                const file = String.fromCharCode('a'.charCodeAt(0) + col);
                const rank = 8 - row;
                const position = `${file}${rank}`;
                const piece = pieces.find((p) => p.position === position);
                const isSelected =
                  selectedPiece && selectedPiece.position === position;

                return (
                  <div
                    key={position}
                    className={`square ${
                      (row + col) % 2 === 0 ? 'light' : 'dark'
                    } ${isSelected ? 'selected' : ''}`}
                    onClick={() => handleSquareClick(position)}
                  >
                    {piece && (
                      <div className={`piece ${piece.color}`}>
                        {piece.type === 'pawn' && '♙'}
                        {piece.type === 'rook' && '♖'}
                        {piece.type === 'knight' && '♘'}
                        {piece.type === 'bishop' && '♗'}
                        {piece.type === 'queen' && '♕'}
                        {piece.type === 'king' && '♔'}
                      </div>
                    )}
                  </div>
                );
              })
          )}
      </div>
    </div>
  );
};

export default ChessBoard;
