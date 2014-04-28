new Thread()
{
		while (!ClientManager.isFinishGame()) {
			// thread che alterna le immagini per dare l'animazione al player2

			while (GameManagerImpl.isPaused()) {
				System.out.println("Sono in pausa  - ImageProvider");
				GameManagerImpl.waitForCondition();
			}

			// //////////////////////////////////////////////

			if (ImageProvider.currentRunningUpPlayer2 == 0) {
				imageSwitchedUpRunning = player2UpRunning;
				currentRunningUpPlayer2 = 1;
			} else if (ImageProvider.currentRunningUpPlayer2 == 1) {
				imageSwitchedUpRunning = player2UpRunning2;
				currentRunningUpPlayer2 = 2;
			} else if (ImageProvider.currentRunningUpPlayer2 == 2) {
				imageSwitchedUpRunning = player2UpRunning3;
				currentRunningUpPlayer2 = 3;
			} else if (ImageProvider.currentRunningUpPlayer2 == 3) {
				imageSwitchedUpRunning = player2UpRunning4;
				currentRunningUpPlayer2 = 0;
			}// Up Running If-Block

			// //////////////////////////////////////////////

			if (ImageProvider.currentRunningDownPlayer2 == 0) {
				imageSwitchedDownRunning = player2DownRunning;
				currentRunningDownPlayer2 = 1;
			} else if (ImageProvider.currentRunningDownPlayer2 == 1) {
				imageSwitchedDownRunning = player2DownRunning2;
				currentRunningDownPlayer2 = 2;
			} else if (ImageProvider.currentRunningDownPlayer2 == 2) {
				imageSwitchedDownRunning = player2DownRunning3;
				currentRunningDownPlayer2 = 3;
			} else if (ImageProvider.currentRunningDownPlayer2 == 3) {
				imageSwitchedDownRunning = player2DownRunning4;
				currentRunningDownPlayer2 = 0;
			}// Down Running If-Block

			// ////////////////////////////////////////////////

			if (ImageProvider.currentRunningRightPlayer2 == 0) {
				imageSwitchedRightRunning = player2RightRunning;
				currentRunningRightPlayer2 = 1;
			} else if (ImageProvider.currentRunningRightPlayer2 == 1) {
				imageSwitchedRightRunning = player2RightRunning2;
				currentRunningRightPlayer2 = 2;
			} else if (ImageProvider.currentRunningRightPlayer2 == 2) {
				imageSwitchedRightRunning = player2RightRunning3;
				currentRunningRightPlayer2 = 3;
			} else if (ImageProvider.currentRunningRightPlayer2 == 3) {
				imageSwitchedRightRunning = player2RightRunning4;
				currentRunningRightPlayer2 = 0;
			}// Right Running If-Block

			// //////////////////////////////////////////////////

			if (ImageProvider.currentRunningLeftPlayer2 == 0) {
				imageSwitchedLeftRunning = player2LeftRunning;
				currentRunningLeftPlayer2 = 1;
			} else if (ImageProvider.currentRunningLeftPlayer2 == 1) {
				imageSwitchedLeftRunning = player2LeftRunning2;
				currentRunningLeftPlayer2 = 2;
			} else if (ImageProvider.currentRunningLeftPlayer2 == 2) {
				imageSwitchedLeftRunning = player2LeftRunning3;
				currentRunningLeftPlayer2 = 3;
			} else if (ImageProvider.currentRunningLeftPlayer2 == 3) {
				imageSwitchedLeftRunning = player2LeftRunning4;
				currentRunningLeftPlayer2 = 0;
			}// Left Running If-Block

			/*--------------------------------------------------------------------------------------*/

			try {
				// valore originario 350
				sleep(350);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}
};