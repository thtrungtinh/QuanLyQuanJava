USE RMS
GO
/****** Object:  StoredProcedure [dbo].[P01L00100_CheckDelete]    Script Date: 05/03/2016 16:31:08 ******/
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
CREATE PROCEDURE [dbo].VITRI_CheckDelete
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




