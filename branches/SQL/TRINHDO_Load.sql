USE [RMS]
GO
/****** Object:  StoredProcedure [dbo].[TRINHDO_Load]    Script Date: 05/04/2016 17:09:25 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO

-- <Summary>
---- load danh sach trinh do
-- <Param>
---- 
-- <Return>
---- 
-- <Reference>
---- Noi goi: Danh muc\trinh do # load
-- <History>
---- Created by Tinh Tran 03/05/2016 
-- <Example>
---- exec TRINHDO_Load
ALTER PROCEDURE [dbo].[TRINHDO_Load]

AS
SET TRANSACTION ISOLATION LEVEL READ UNCOMMITTED

BEGIN
	SELECT * FROM TRINHDO t
END




