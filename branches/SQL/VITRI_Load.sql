USE [RMS]
GO
/****** Object:  StoredProcedure [dbo].[VITRI_Load]    Script Date: 05/04/2016 17:10:03 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
-- exec VITRI_Load
ALTER PROCEDURE [dbo].[VITRI_Load]
	AS
BEGIN
	SELECT * FROM VITRI v
	
END
