USE [RMS]
GO
/****** Object:  StoredProcedure [dbo].[TRINHDO_CheckEdit]    Script Date: 05/04/2016 17:09:08 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO

-- <Summary>
---- Kiem tra co duoc xoa danh muc
-- <Param>
---- 
-- <Return>
---- 
-- <Reference>
---- Noi goi: Danh muc\Vi tri # sua
-- <History>
---- Created by Tinh Tran 03/05/2016 
-- <Example>
---- TRINHDO_CheckEdit @MaViTri=N'', @Message=''
ALTER PROCEDURE [dbo].[TRINHDO_CheckEdit]
(
	@MaTrinhDo AS NVARCHAR(50),
	@Message AS NVARCHAR(4000) OUTPUT
)
AS
SET TRANSACTION ISOLATION LEVEL READ UNCOMMITTED
SET @Message = N''
BEGIN
	IF NOT EXISTS (SELECT * FROM TRINHDO t WHERE t.MaTrinhDo = @MaTrinhDo)
	BEGIN
	SET @Message = N'Mã này không đúng, không thể sửa !'	
	END
	PRINT @Message
	
END




