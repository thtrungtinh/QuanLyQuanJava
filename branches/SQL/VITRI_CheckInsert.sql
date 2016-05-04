USE [RMS]
GO
/****** Object:  StoredProcedure [dbo].[VITRI_CheckInsert]    Script Date: 05/04/2016 17:09:54 ******/
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
---- Noi goi: Danh muc\Vi tri # them
-- <History>
---- Created by Tinh Tran 03/05/2016 
-- <Example>
---- VITRI_CheckInsert @MaViTri=N'', @Message=''
ALTER PROCEDURE [dbo].[VITRI_CheckInsert]
(
	@MaViTri AS NVARCHAR(50),
	@Message AS NVARCHAR(4000) OUTPUT
)
AS
SET TRANSACTION ISOLATION LEVEL READ UNCOMMITTED
SET @Message = N''
BEGIN
	IF EXISTS (SELECT * FROM VITRI v WHERE v.MaViTri = @MaViTri)
	BEGIN
	SET @Message = N'Mã này đã được sử dụng, không thể thêm !'	
	END
	PRINT @Message
	
END




