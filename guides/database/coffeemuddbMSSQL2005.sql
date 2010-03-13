USE [coffeemud]
GO
/****** Object:  Table [dbo].[CMAREA]    Script Date: 09/27/2007 11:15:17 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [dbo].[CMAREA](
	[CMAREA] [char](50) NOT NULL,
	[CMTYPE] [char](50) NULL,
	[CMCLIM] [int] NULL,
	[CMSUBS] [char](100) NULL,
	[CMDESC] [text] NULL,
	[CMROTX] [text] NULL,
	[CMTECH] [int] NULL,
 CONSTRAINT [PK_CMAREA] PRIMARY KEY CLUSTERED 
(
	[CMAREA] ASC
)WITH (PAD_INDEX  = OFF, STATISTICS_NORECOMPUTE  = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS  = ON, ALLOW_PAGE_LOCKS  = ON) ON [PRIMARY]
) ON [PRIMARY] TEXTIMAGE_ON [PRIMARY]

GO
SET ANSI_PADDING OFF
GO
/****** Object:  Table [dbo].[CMCCAC]    Script Date: 09/27/2007 11:15:17 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [dbo].[CMCCAC](
	[CMCCID] [char](50) NOT NULL,
	[CMCDAT] [text] NULL,
 CONSTRAINT [PK_CMCCAC] PRIMARY KEY CLUSTERED 
(
	[CMCCID] ASC
)WITH (PAD_INDEX  = OFF, STATISTICS_NORECOMPUTE  = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS  = ON, ALLOW_PAGE_LOCKS  = ON) ON [PRIMARY]
) ON [PRIMARY] TEXTIMAGE_ON [PRIMARY]

GO
SET ANSI_PADDING OFF
GO
/****** Object:  Table [dbo].[CMCHAB]    Script Date: 09/27/2007 11:15:17 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [dbo].[CMCHAB](
	[CMUSERID] [char](50) NOT NULL,
	[CMABID] [char](50) NOT NULL,
	[CMABPF] [int] NULL,
	[CMABTX] [text] NULL,
 CONSTRAINT [PK_CMCHAB] PRIMARY KEY CLUSTERED 
(
	[CMUSERID] ASC,
	[CMABID] ASC
)WITH (PAD_INDEX  = OFF, STATISTICS_NORECOMPUTE  = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS  = ON, ALLOW_PAGE_LOCKS  = ON) ON [PRIMARY]
) ON [PRIMARY] TEXTIMAGE_ON [PRIMARY]

GO
SET ANSI_PADDING OFF
GO
/****** Object:  Table [dbo].[CMCHAR]    Script Date: 09/27/2007 11:15:17 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [dbo].[CMCHAR](
	[CMUSERID] [char](50) NOT NULL,
	[CMPASS] [char](50) NULL,
	[CMCLAS] [char](200) NULL,
	[CMSTRE] [int] NULL,
	[CMRACE] [char](50) NULL,
	[CMDEXT] [int] NULL,
	[CMCONS] [int] NULL,
	[CMGEND] [char](50) NULL,
	[CMWISD] [int] NULL,
	[CMINTE] [int] NULL,
	[CMCHAR] [int] NULL,
	[CMHITP] [int] NULL,
	[CMLEVL] [char](50) NULL,
	[CMMANA] [int] NULL,
	[CMMOVE] [int] NULL,
	[CMDESC] [char](255) NULL,
	[CMALIG] [int] NULL,
	[CMEXPE] [int] NULL,
	[CMEXLV] [int] NULL,
	[CMWORS] [char](50) NULL,
	[CMPRAC] [int] NULL,
	[CMTRAI] [int] NULL,
	[CMAGEH] [int] NULL,
	[CMGOLD] [int] NULL,
	[CMWIMP] [int] NULL,
	[CMQUES] [int] NULL,
	[CMROID] [char](100) NULL,
	[CMDATE] [char](50) NULL,
	[CMCHAN] [int] NULL,
	[CMATTA] [int] NULL,
	[CMAMOR] [int] NULL,
	[CMDAMG] [int] NULL,
	[CMBTMP] [int] NULL,
	[CMLEIG] [char](50) NULL,
	[CMHEIT] [int] NULL,
	[CMWEIT] [int] NULL,
	[CMPRPT] [char](250) NULL,
	[CMCOLR] [char](100) NULL,
	[CMLSIP] [char](100) NULL,
	[CMCLAN] [char](100) NULL,
	[CMCLRO] [int] NULL,
	[CMEMAL] [char](255) NULL,
	[CMPFIL] [text] NULL,
	[CMSAVE] [char](150) NULL,
	[CMMXML] [text] NULL,
 CONSTRAINT [PK_CMCHAR] PRIMARY KEY CLUSTERED 
(
	[CMUSERID] ASC
)WITH (PAD_INDEX  = OFF, STATISTICS_NORECOMPUTE  = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS  = ON, ALLOW_PAGE_LOCKS  = ON) ON [PRIMARY]
) ON [PRIMARY] TEXTIMAGE_ON [PRIMARY]

GO
SET ANSI_PADDING OFF
GO
/****** Object:  Table [dbo].[CMCHFO]    Script Date: 09/27/2007 11:15:17 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [dbo].[CMCHFO](
	[CMUSERID] [char](50) NOT NULL,
	[CMFONM] [int] NOT NULL,
	[CMFOID] [char](50) NULL,
	[CMFOTX] [text] NULL,
	[CMFOLV] [int] NULL,
	[CMFOAB] [int] NULL,
 CONSTRAINT [PK_CMCHFO] PRIMARY KEY CLUSTERED 
(
	[CMUSERID] ASC,
	[CMFONM] ASC
)WITH (PAD_INDEX  = OFF, STATISTICS_NORECOMPUTE  = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS  = ON, ALLOW_PAGE_LOCKS  = ON) ON [PRIMARY]
) ON [PRIMARY] TEXTIMAGE_ON [PRIMARY]

GO
SET ANSI_PADDING OFF
GO
/****** Object:  Table [dbo].[CMCHIT]    Script Date: 09/27/2007 11:15:17 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [dbo].[CMCHIT](
	[CMUSERID] [char](50) NOT NULL,
	[CMITNM] [char](100) NOT NULL,
	[CMITID] [char](50) NULL,
	[CMITTX] [text] NULL,
	[CMITLO] [char](100) NULL,
	[CMITWO] [int] NULL,
	[CMITUR] [int] NULL,
	[CMITLV] [int] NULL,
	[CMITAB] [int] NULL,
	[CMHEIT] [int] NULL,
 CONSTRAINT [PK_CMCHIT] PRIMARY KEY CLUSTERED 
(
	[CMUSERID] ASC,
	[CMITNM] ASC
)WITH (PAD_INDEX  = OFF, STATISTICS_NORECOMPUTE  = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS  = ON, ALLOW_PAGE_LOCKS  = ON) ON [PRIMARY]
) ON [PRIMARY] TEXTIMAGE_ON [PRIMARY]

GO
SET ANSI_PADDING OFF
GO
/****** Object:  Table [dbo].[CMCLAN]    Script Date: 09/27/2007 11:15:17 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [dbo].[CMCLAN](
	[CMCLID] [char](100) NOT NULL,
	[CMTYPE] [int] NULL,
	[CMDESC] [text] NULL,
	[CMACPT] [char](255) NULL,
	[CMPOLI] [text] NULL,
	[CMRCLL] [char](50) NULL,
	[CMDNAT] [char](50) NULL,
	[CMSTAT] [int] NULL,
	[CMMORG] [char](50) NULL,
	[CMTROP] [int] NULL,
 CONSTRAINT [PK_CMCLAN] PRIMARY KEY CLUSTERED 
(
	[CMCLID] ASC
)WITH (PAD_INDEX  = OFF, STATISTICS_NORECOMPUTE  = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS  = ON, ALLOW_PAGE_LOCKS  = ON) ON [PRIMARY]
) ON [PRIMARY] TEXTIMAGE_ON [PRIMARY]

GO
SET ANSI_PADDING OFF
GO
/****** Object:  Table [dbo].[CMGAAC]    Script Date: 09/27/2007 11:15:17 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [dbo].[CMGAAC](
	[CMGAID] [char](50) NOT NULL,
	[CMGAAT] [text] NULL,
 CONSTRAINT [PK_CMGAAC] PRIMARY KEY CLUSTERED 
(
	[CMGAID] ASC
)WITH (PAD_INDEX  = OFF, STATISTICS_NORECOMPUTE  = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS  = ON, ALLOW_PAGE_LOCKS  = ON) ON [PRIMARY]
) ON [PRIMARY] TEXTIMAGE_ON [PRIMARY]

GO
SET ANSI_PADDING OFF
GO
/****** Object:  Table [dbo].[CMGRAC]    Script Date: 09/27/2007 11:15:17 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [dbo].[CMGRAC](
	[CMRCID] [char](50) NOT NULL,
	[CMRDAT] [text] NULL,
 CONSTRAINT [PK_CMGRAC] PRIMARY KEY CLUSTERED 
(
	[CMRCID] ASC
)WITH (PAD_INDEX  = OFF, STATISTICS_NORECOMPUTE  = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS  = ON, ALLOW_PAGE_LOCKS  = ON) ON [PRIMARY]
) ON [PRIMARY] TEXTIMAGE_ON [PRIMARY]

GO
SET ANSI_PADDING OFF
GO
/****** Object:  Table [dbo].[CMJRNL]    Script Date: 09/27/2007 11:15:17 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [dbo].[CMJRNL](
	[CMJKEY] [char](255) NOT NULL,
	[CMJRNL] [char](50) NULL,
	[CMFROM] [char](50) NULL,
	[CMDATE] [char](50) NULL,
	[CMTONM] [char](50) NULL,
	[CMSUBJ] [char](255) NULL,
	[CMMSGT] [text] NULL,
 CONSTRAINT [PK_CMJRNL] PRIMARY KEY CLUSTERED 
(
	[CMJKEY] ASC
)WITH (PAD_INDEX  = OFF, STATISTICS_NORECOMPUTE  = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS  = ON, ALLOW_PAGE_LOCKS  = ON) ON [PRIMARY]
) ON [PRIMARY] TEXTIMAGE_ON [PRIMARY]

GO
SET ANSI_PADDING OFF
GO
/****** Object:  Table [dbo].[CMPDAT]    Script Date: 09/27/2007 11:15:17 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [dbo].[CMPDAT](
	[CMPLID] [char](100) NOT NULL,
	[CMSECT] [char](100) NOT NULL,
	[CMPKEY] [char](100) NOT NULL,
	[CMPDAT] [text] NULL,
 CONSTRAINT [PK_CMPDAT] PRIMARY KEY CLUSTERED 
(
	[CMPLID] ASC,
	[CMSECT] ASC,
	[CMPKEY] ASC
)WITH (PAD_INDEX  = OFF, STATISTICS_NORECOMPUTE  = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS  = ON, ALLOW_PAGE_LOCKS  = ON) ON [PRIMARY]
) ON [PRIMARY] TEXTIMAGE_ON [PRIMARY]

GO
SET ANSI_PADDING OFF
GO
/****** Object:  Table [dbo].[CMPOLL]    Script Date: 09/27/2007 11:15:17 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [dbo].[CMPOLL](
	[CMNAME] [char](100) NOT NULL,
	[CMBYNM] [char](100) NULL,
	[CMSUBJ] [char](255) NULL,
	[CMDESC] [text] NULL,
	[CMOPTN] [text] NULL,
	[CMFLAG] [bigint] NULL,
	[CMQUAL] [char](255) NULL,
	[CMRESL] [text] NULL,
	[CMEXPI] [bigint] NULL,
 CONSTRAINT [PK_CMPOLL] PRIMARY KEY CLUSTERED 
(
	[CMNAME] ASC
)WITH (PAD_INDEX  = OFF, STATISTICS_NORECOMPUTE  = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS  = ON, ALLOW_PAGE_LOCKS  = ON) ON [PRIMARY]
) ON [PRIMARY] TEXTIMAGE_ON [PRIMARY]

GO
SET ANSI_PADDING OFF
GO
/****** Object:  Table [dbo].[CMQUESTS]    Script Date: 09/27/2007 11:15:17 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [dbo].[CMQUESTS](
	[CMQUESID] [char](50) NOT NULL,
	[CMQUTYPE] [char](50) NULL,
	[CMQSCRPT] [text] NULL,
	[CMQWINNS] [text] NULL,
 CONSTRAINT [PK_CMQUESTS] PRIMARY KEY CLUSTERED 
(
	[CMQUESID] ASC
)WITH (PAD_INDEX  = OFF, STATISTICS_NORECOMPUTE  = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS  = ON, ALLOW_PAGE_LOCKS  = ON) ON [PRIMARY]
) ON [PRIMARY] TEXTIMAGE_ON [PRIMARY]

GO
SET ANSI_PADDING OFF
GO
/****** Object:  Table [dbo].[CMROCH]    Script Date: 09/27/2007 11:15:17 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [dbo].[CMROCH](
	[CMROID] [char](50) NOT NULL,
	[CMCHNM] [char](100) NOT NULL,
	[CMCHID] [char](50) NULL,
	[CMCHTX] [text] NULL,
	[CMCHLV] [int] NULL,
	[CMCHAB] [int] NULL,
	[CMCHRE] [int] NULL,
	[CMCHRI] [char](100) NULL,
 CONSTRAINT [PK_CMROCH] PRIMARY KEY CLUSTERED 
(
	[CMROID] ASC,
	[CMCHNM] ASC
)WITH (PAD_INDEX  = OFF, STATISTICS_NORECOMPUTE  = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS  = ON, ALLOW_PAGE_LOCKS  = ON) ON [PRIMARY]
) ON [PRIMARY] TEXTIMAGE_ON [PRIMARY]

GO
SET ANSI_PADDING OFF
GO
/****** Object:  Table [dbo].[CMROEX]    Script Date: 09/27/2007 11:15:17 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [dbo].[CMROEX](
	[CMROID] [char](50) NOT NULL,
	[CMDIRE] [int] NOT NULL,
	[CMEXID] [char](50) NULL,
	[CMEXTX] [text] NULL,
	[CMNRID] [char](50) NULL,
 CONSTRAINT [PK_CMROEX] PRIMARY KEY CLUSTERED 
(
	[CMROID] ASC,
	[CMDIRE] ASC
)WITH (PAD_INDEX  = OFF, STATISTICS_NORECOMPUTE  = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS  = ON, ALLOW_PAGE_LOCKS  = ON) ON [PRIMARY]
) ON [PRIMARY] TEXTIMAGE_ON [PRIMARY]

GO
SET ANSI_PADDING OFF
GO
/****** Object:  Table [dbo].[CMROIT]    Script Date: 09/27/2007 11:15:17 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [dbo].[CMROIT](
	[CMROID] [char](50) NOT NULL,
	[CMITNM] [char](100) NOT NULL,
	[CMITID] [char](50) NULL,
	[CMITLO] [char](100) NULL,
	[CMITTX] [text] NULL,
	[CMITRE] [int] NULL,
	[CMITUR] [int] NULL,
	[CMITLV] [int] NULL,
	[CMITAB] [int] NULL,
	[CMHEIT] [int] NULL,
 CONSTRAINT [PK_CMROIT] PRIMARY KEY CLUSTERED 
(
	[CMROID] ASC,
	[CMITNM] ASC
)WITH (PAD_INDEX  = OFF, STATISTICS_NORECOMPUTE  = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS  = ON, ALLOW_PAGE_LOCKS  = ON) ON [PRIMARY]
) ON [PRIMARY] TEXTIMAGE_ON [PRIMARY]

GO
SET ANSI_PADDING OFF
GO
/****** Object:  Table [dbo].[CMROOM]    Script Date: 09/27/2007 11:15:17 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [dbo].[CMROOM](
	[CMROID] [char](50) NOT NULL,
	[CMLOID] [char](50) NULL,
	[CMAREA] [char](50) NULL,
	[CMDESC1] [char](255) NULL,
	[CMDESC2] [text] NULL,
	[CMROTX] [text] NULL,
 CONSTRAINT [PK_CMROOM] PRIMARY KEY CLUSTERED 
(
	[CMROID] ASC
)WITH (PAD_INDEX  = OFF, STATISTICS_NORECOMPUTE  = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS  = ON, ALLOW_PAGE_LOCKS  = ON) ON [PRIMARY]
) ON [PRIMARY] TEXTIMAGE_ON [PRIMARY]

GO
SET ANSI_PADDING OFF
GO
/****** Object:  Table [dbo].[CMSTAT]    Script Date: 09/27/2007 11:15:17 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[CMSTAT](
	[CMSTRT] [bigint] NOT NULL,
	[CMENDT] [bigint] NULL,
	[CMDATA] [text] NULL,
 CONSTRAINT [PK_CMSTAT] PRIMARY KEY CLUSTERED 
(
	[CMSTRT] ASC
)WITH (PAD_INDEX  = OFF, STATISTICS_NORECOMPUTE  = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS  = ON, ALLOW_PAGE_LOCKS  = ON) ON [PRIMARY]
) ON [PRIMARY] TEXTIMAGE_ON [PRIMARY]

GO
/****** Object:  Table [dbo].[CMVFS]    Script Date: 09/27/2007 11:15:17 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [dbo].[CMVFS](
	[CMFNAM] [char](255) NOT NULL,
	[CMDTYP] [int] NULL,
	[CMMODD] [bigint] NULL,
	[CMWHOM] [char](50) NULL,
	[CMDATA] [text] NULL,
 CONSTRAINT [PK_CMVFS] PRIMARY KEY CLUSTERED 
(
	[CMFNAM] ASC
)WITH (PAD_INDEX  = OFF, STATISTICS_NORECOMPUTE  = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS  = ON, ALLOW_PAGE_LOCKS  = ON) ON [PRIMARY]
) ON [PRIMARY] TEXTIMAGE_ON [PRIMARY]

GO
SET ANSI_PADDING OFF