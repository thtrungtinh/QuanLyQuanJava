USE [RMS]
GO
/****** Object:  StoredProcedure [dbo].[VITRI_CheckDelete]    Script Date: 05/04/2016 17:09:34 ******/
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
---- Noi goi: Danh muc\Vi tri # xoa
-- <History>
---- Created by Tinh Tran 03/05/2016 
-- <Example>
---- VITRI_CheckDelete @MaViTri=N'', @Message=''
ALTER PROCEDURE [dbo].[VITRI_CheckDelete]
(
	@MaViTri AS NVARCHAR(50),
	@Message AS NVARCHAR(4000) OUTPUT
)
AS
SET TRANSACTION ISOLATION LEVEL READ UNCOMMITTED
SET @Message = N''
BEGIN
	IF NOT EXISTS (SELECT * FROM VITRI v WHERE v.MaViTri = @MaViTri)
	BEGIN
	SET @Message = N'Mã này không đúng, không thể xóa !'	
	END
	PRINT @Message
	
END




